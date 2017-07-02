InScribe Secure SMS

A very simple application for sending, receiving, viewing a secure short message service for Android Platform.
The message is sent and received via standard SMS protocol that does not require internet connection allowing for reliable yet secured communication even in the remote area where the celular signal is very limited.

Encryption is performed using a chiper suite consists of ECDHE-RSA-AES-SHA.
* ECDHE (configurable for 128-bit, 192-bit, 256-bit) stands for Elliptic Curve Diffie-Hellman Ephimeral. It is used as a key exchange protocol to derive the symmetric key to encrypt the message using elliptic curve cryptography. The ephimeral means that the key is temporary for one session only thus a handshake must be reinitiated after a session has been ended.
* The RSA (configurable for 1024-bit, 2048-bit, 4096-bit) is used for digital signature during the key exchange to validate the one who send the ECDHE Public Key. Both of party must save each other's RSA Public Key. The RSA Public Key can be obtained by using public key services (such as pgp.mit.edu) or by meet directly with the person who he wish to communicate. Then, by using the QR Code scanner, he can add a new contact (including its number and RSA Public Key) to the database. As long as the RSA Public Key is not changed, the key exchange can be performed.
* The AES (configurable for 128-bit, 192-bit, 256-bit) is used to encrypt the message where the AES key is derived from the shared secret obtained using ECDHE protocol by using PBKDF2 key derivation function. As long as the session is active, the same AES key is used to encrypt the message. On the other hand, the IV itself in CBC mode is automatically regenerated for each message.
* The SHA (configurable for 1, 2, 256) is used during PBKDF2 derivation function.

This application is still under development. Since this is my first time building an Android application using Android Studio, I believe that there are a lot of bug that must be improved. The UX design itself is very simple. The application itself will be used as the final project for EL5111 Cryptography and Its Application class in Institut Teknologi Bandung.

Any suggestion and improvement are highly appreciated.

(C)2017 Bagus Hanindhito

