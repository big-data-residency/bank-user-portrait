
popup = document.getElementById('popup');
closeBtn = document.getElementById('popup-close');
openBtn = document.getElementById('popup-open');

let closePopup = () => {
  popup.style.display = 'none';
}
let openPopup = () => {
  popup.style.display = 'block';
}

closeBtn.addEventListener('click', closePopup);
openBtn.addEventListener('click', openPopup);