/*
 * Make the link between the displayed readonly input text and the real input file.
 * So that when a new file is selected, it's name is displayed in the input text.
 */
function linkFileInput(fileInputId, textInputId){
	var input = document.getElementById(fileInputId);
	$("#"+fileInputId).change( function (e){
		$("#"+textInputId).val(e.target.value.split( '\\' ).pop());
	});	
}

$(document).ready(function (){
		linkFileInput("statementImgFile", "statementImg");
		linkFileInput("statementAudioFile", "statementAudio");
		linkFileInput("answerImgFile", "answerImg");
		linkFileInput("answerAudioFile", "answerAudio");
});