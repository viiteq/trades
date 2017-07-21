function validate() {
	$('#response').val('');
    $.ajax({
    	type: "POST",
        url: $('#service-url').val(),
        contentType: "application/json; charset=utf-8",
        data: $('#request').val(),
        success: function (data) {
        	$('#response').val(JSON.stringify(data));          
        },
        error: function (xhr, status) {
        	$('#response').val('Error while processing request, response status: ' + xhr.status);  
        }
    });
};
