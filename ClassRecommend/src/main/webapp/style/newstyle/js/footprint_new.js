/* interval timer */
const
  timer = 10000,
  ui = document.getElementById('ui');

setInterval(function() {
  ui.classList.toggle('switch');
}, timer);