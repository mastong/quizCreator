## Quiz Creator

A simple web application to manage "quizzes".

A quiz is a list of questions that can be used in another application

A question is, at minimum:

- a statement
- an answer
- a point value

A question can also be more complex and include audio/image for the statement and/or the answer 

## TODO

### For the edit page :
- Add Jquery and then update the input "statementImg" with the file selected (the javascript must go to file.js, and be generic so that the same method can be used on edit/create, for each file upload).
- Move the css in style attribute to a css file
- Rework the css so that the label text for the upload is in one line with no break

### For the others feats
- add a page to manage the account (change password,...)
- add the possibility to have multiple quiz
- use the templating for the view, so that the header/footer can be factorized
- use real I18n and not hard coded message
- display the user name on the quiz page
- make it attractive :)

## BUGS

- The FileUploadBase$FileSizeLimitExceededException is not handle correctly. Currently an error page is displayed when the file is more then 1048576 bytes
