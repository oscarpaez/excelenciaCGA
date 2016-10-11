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

function showLoading() {
	 PF('statusDialog').show();
}

function showCliente(){
	
	
	//alert(PF('test').getSelectedValue());
		
	if(PF('test').getSelectedValue() == 0){
		//alert("listo");  
		PF('test').jq.hide();
		PF('cliOtro').jq.show();
		$('#cli').show();
		$('#cli1').hide();
	}	
	
}

function showMaterial(){
	
	if(PF('mate').getSelectedValue() == 0){
		//alert("listo");  
		PF('mate').jq.hide();
		PF('mateOtro').jq.show();
		$('#mate1').show();
		$('#mateL').hide();
	}
	
}
