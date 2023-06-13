const apiKey = 'AZPViD46vTWepGB2bF1UTz'; //This key need to place in back-end.

const uploadBtn = document.getElementById('fileUpload');

let fileName = $("#fileUploaded").val();

console.log(fileName);

uploadBtn.addEventListener('click', function (e){
    e.preventDefault();
    const client = filestack.init(apiKey);
    const options = {
        uploadInBackground: false, // Wait for the upload to finish before triggering onUploadDone
        onFileUploadFinished: (fileFinish) => {
            // console.log('File upload finished:', fileFinish);

            let fileName = fileFinish.filename;
            let fileURL = fileFinish.url;
            console.log(fileName);
            console.log(fileURL);

            document.getElementById("inputURL").value = fileURL;

            let pushFile = '';
            pushFile += "<ul><li>"+ fileName +"</li></ul>"
            $("#fileUploaded").html(pushFile);

        },
        onFileUploadFailed: (file, error) => {
            console.error('File upload failed:', error);
        },
    };
    client.picker(options).open();

})