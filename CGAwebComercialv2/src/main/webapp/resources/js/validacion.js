function verificar(xhr, status, args, dlg, tbl) {
	if(args.validationFailed) {
		PF(dlg).jq.effect("shake", {
			times:5
			}, 100);
	}
	else {
		PF(dlg).hide();
		PF(tbl).clearFilters();
	}
}

function ocultar(id){
	
	//alert("entro" + id);	
	$('#messages').hide(8000);	
}


function handleLoginRequest(xhr, status, args) {
    if(args.validationFailed || !args.loggedIn) {
        PF('grid').jq.effect("shake", {times:5}, 100);
    }
    else {
    	
        /*PF('grid').hide();
        $('#loginLink').fadeOut();*/
    	alert("entro en false");
    }
}

function start(){
	$('.animated').show();
	$('#pbAjaxText').show();
}
