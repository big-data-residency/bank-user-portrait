(function(factory) {
    if (typeof define === 'function' && define.amd) {
        define(['jquery'], factory);
    } else if (typeof exports === 'object') {
        factory(require('jquery'));
    } else {
        factory(jQuery);
    }
})
(function($) {
    'use strict';
    var console = window.console || {
        log: function() {}
    };
    // 定义变量
    function CropAvatar($element) {
        this.$container = $element;
        this.$avatarView = this.$container.find('.avatar-view');
        this.$avatar = this.$avatarView.find('img');
        this.$avatarModal = $("body").find('#avatar-modal');
        this.$loading = $("#page-wrapper").find('.loading');
        this.$avatarForm = this.$avatarModal.find('.avatar-form');
        this.$avatarUpload = this.$avatarForm.find('.avatar-upload');
        this.$avatarSrc = this.$avatarForm.find('.avatar-src');
        this.$avatarData = this.$avatarForm.find('.avatar-data');
        this.$avatarInput = this.$avatarForm.find('.avatar-input');
        this.$avatarSave = this.$avatarForm.find('.avatar-save');
        this.$avatarBtns = this.$avatarForm.find('.avatar-btns');
        this.$avatarWrapper = this.$avatarModal.find('.avatar-wrapper');
        this.$avatarPreview = this.$avatarModal.find('.avatar-preview');
        this.$avatarUploadBtn = this.$container.find('#avatar-upload-btn');
        this.init();
    }
    CropAvatar.prototype = {
        constructor: CropAvatar,
        support: {
            fileList: !!$('<input type="file">').prop('files'),
            blobURLs: !!window.URL && URL.createObjectURL,
            formData: !!window.FormData
        },
        init: function() {
            this.support.datauri = this.support.fileList && this.support.blobURLs;
            if (!this.support.formData) {
                this.initIframe();
            }
            this.initTooltip();
            this.initModal();
            this.addListener();
            console.log(this.$avatarForm);
            console.log(this.$avatarData);
        },
        addListener: function() {
            this.$avatarView.on('click', $.proxy(this.click, this));
            this.$avatarUploadBtn.on('click', $.proxy(this.click, this));
            this.$avatarInput.on('change', $.proxy(this.change, this));
            this.$avatarForm.on('submit', $.proxy(this.submit, this));
            this.$avatarBtns.on('click', $.proxy(this.rotate, this));
        },
        initTooltip: function() {
            this.$avatarView.tooltip({
                placement: 'bottom'
            });
        },
        initModal: function() {
            this.$avatarModal.modal({
                show: false
            });
        },
        initPreview: function() {
            console.log("initPreview");
            var url = this.$avatar.attr('src');
            this.$avatarPreview.empty().html('<img src="' + url + '">');
        },
        initIframe: function() {
            console.log("initIframe")
            var target = 'upload-iframe-' + (new Date()).getTime(),
                $iframe = $('<iframe>').attr({
                    name: target,
                    src: ''
                }),
                _this = this;
            $iframe.one('load', function() {
                $iframe.on('load', function() {
                    var data;
                    try {
                        data = $(this).contents().find('body').text();
                    } catch (e) {
                        console.log(e.message);
                    }
                    if (data) {
                        try {
                            data = $.parseJSON(data);
                        } catch (e) {
                            console.log(e.message);
                        }
                        _this.submitDone(data);
                    } else {
                        _this.submitFail('Image upload failed!');
                    }
                    _this.submitEnd();
                });
            });
            this.$iframe = $iframe;
            this.$avatarForm.attr('target', target).after($iframe.hide());
        },
        click: function() {
            console.log("click");
            this.$avatarModal.modal('show');
            this.initPreview();
        },
        change: function() {
            console.log("change");
            var files, file;
            if (this.support.datauri) {
                files = this.$avatarInput.prop('files');
                if (files.length > 0) {
                    file = files[0];
                    if (this.isImageFile(file)) {
                        if (this.url) {
                            URL.revokeObjectURL(this.url);
                        }
                        this.url = URL.createObjectURL(file);
                        this.startCropper();
                    }
                }
            } else {
                file = this.$avatarInput.val();
                if (this.isImageFile(file)) {
                    this.syncUpload();
                }
            }
        },
        submit: function() {
            console.log("submit");
            if (!this.$avatarSrc.val() && !this.$avatarInput.val()) {
                return false;
            }
            if (this.support.formData) {
                this.ajaxUpload();
                return false;
            }
        },
        rotate: function(e) {
            var data;
            if (this.active) {
                data = $(e.target).data();
                if (data.method) {
                    this.$img.cropper(data.method, data.option);
                }
            }
        },
        isImageFile: function(file) {
            if (file.type) {
                return /^image\/\w+$/.test(file.type);
            } else {
                return /\.(jpg|jpeg|png|gif)$/.test(file);
            }
        },
        startCropper: function() {
            console.log("startCropper");
            var _this = this;
            if (this.active) {
                this.$img.cropper('replace', this.url);
            } else {
                this.$img = $('<img src="' + this.url + '">');
                console.log(this.$img);
                this.$avatarWrapper.empty().html(this.$img);
                this.$img.cropper({
                    aspectRatio: 1,
                    preview: '.avatar-preview', //this.$avatarPreview.selector deprecated
                    strict: false,
                    crop: function(data) {
                        var json = ['{"x":' + data.x, '"y":' + data.y, '"height":' + data.height, '"width":' + data.width, '"rotate":' + data.rotate + '}'].join();
                        _this.$avatarData.val(json);
                    }
                });
                this.active = true;
            }
        },
        stopCropper: function() {
            console.log("stopCropper")
            if (this.active) {
                this.$img.cropper('destroy');
                this.$img.remove();
                this.active = false;
            }
        },
        ajaxUpload: function() {
            var url = this.$avatarForm.attr('action'),
                data = new FormData(this.$avatarForm[0]),
                _this = this;
            $.ajax(url, {
                headers: {
                    "myheader": "header"
                },
                type: 'post',
                data: data,
                dataType: 'json',
                processData: false,
                contentType: false,
                beforeSend: function() {
                    _this.submitStart();
                },
                success: function(data) {
                    _this.submitDone(data);
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    _this.submitFail(textStatus || errorThrown);
                },
                complete: function() {
                    _this.submitEnd();
                }
            });
        },
        syncUpload: function() {
            console.log("syncUpload");
            this.$avatarSave.click();
        },
        submitStart: function() {
            console.log("submitStart");
            this.$loading.fadeIn();
        },
        submitDone: function(data) {
            console.log("submitDone");
            console.log(data);
            if ($.isPlainObject(data)) {
                if (data.result) {
                    this.url = data.result;
                    console.log("this.uploaded");
                    console.log(this.uploaded);
                    console.log(this.support.datauri);
                    if (this.support.datauri || this.uploaded) {
                        this.uploaded = false;
                        this.cropDone();
                    } else {
                        this.uploaded = true;
                        this.$avatarSrc.val(this.url);
                        this.startCropper();
                    }
                    this.$avatarInput.val('');
                } else if (data.message) {
                    this.alert(data.message);
                }
            } else {
                this.alert('Failed to response');
            }
        },
        submitFail: function(msg) {
            console.log("submitFail");
            this.alert(msg);
        },
        submitEnd: function() {
            console.log("submitEnd")
            this.$loading.fadeOut();
        },
        cropDone: function() {
            console.log("cropDone");
            console.log(this.url);
            this.$avatarForm.get(0).reset();
            this.$avatar.attr('src', this.url);
            this.stopCropper();
            this.$avatarModal.modal('hide');
        },
        alert: function(msg) {
            var $alert = ['<div class="alert alert-danger avater-alert">', '<button type="button" class="close" data-dismiss="alert">&times;</button>', msg, '</div>'].join('');
            this.$avatarUpload.after($alert);
        }
    };
    $(function() {
        console.log("prepare to new CropperAvatar");
        return new CropAvatar($('#crop-avatar'));
    });
});
