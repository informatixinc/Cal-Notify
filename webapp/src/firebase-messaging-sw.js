importScripts('https://www.gstatic.com/firebasejs/3.5.2/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/3.5.2/firebase-messaging.js');

firebase.initializeApp({
  'messagingSenderId': '1081107955861'
});

const messaging = firebase.messaging();

messaging.setBackgroundMessageHandler(function(payload) {
  
  return self.registration.showNotification(payload.notification.title,
      payload.body);
});


self.addEventListener('push', function(event) {

  payload = JSON.parse(event.data.text());
  const options = {
    body: payload.notification.body,
    icon: 'assets/images/state_seal.png'
  };

  event.waitUntil(self.registration.showNotification(payload.notification.title,options));
});