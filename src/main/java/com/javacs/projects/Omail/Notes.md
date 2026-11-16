# Email Protocols
- SMPT (Simple Mail Transfer Protocol): sending email
  - Just for sending, not reading
  - basic version on port 25
  - secure encrypted version on port 465


- IMAP (Internet Message Access Protocol): reading, without removing the email from the server
  - good for using multiply devices
  - basic version on port 143
  - secure encrypted version on port 993


- POP3 (Post Office Protocol version 3): downloading mails and removing from server
  - basic version on port 110
  - secure encrypted version on port 995


Protocol | Job                  | Port | Secure Port
    ---------|----------------------|------|------------
    SMTP     | Send email           | 25   | 465
    IMAP     | Read (stay on server)| 143  | 993
    POP3     | Download (then remove)| 110 | 995

