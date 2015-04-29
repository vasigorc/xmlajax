jQuery.noConflict();
jQuery(function(va) {
    //get the data from input fields and send to the server
    va("#retrieve").click(function() {
        //empty the dynamic content container        
        va("#dynamiccontent").empty();
        //fetch the action selected & value requested by the user        
        var action = va("input[type=radio]:checked").val();
        var usrInput;
        if (action === "Movie Title") {
            usrInput = va("#movieinput").val();
        } else if (action === "Specific Actor") {
            usrInput = va("#actorInput").val();
        } else if (action === "Specific Director") {
            usrInput = va("#directorInput").val();
        } else {
            alert("Couldn't grab the values from Front-End");
        }
        va("#dynamiccontent").load("resultsreceiver", {choice: action, input: usrInput});
    });
//elnarge input fields on focus
    va("input:text, select").bind({
        focus: function() {
            va(this).animate({
                width: '17em',
                height: '2.5em',
                fontSize: '1.2em'
            });
        },
        blur: function() {
            va(this).animate({
                width: '220px',
                height: '30px',
                fontSize: 'inherit'
            });
        }
    });
});