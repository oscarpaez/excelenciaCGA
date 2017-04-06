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

function showHijo() {
	
	var inputs = PF('hijo').inputs;
	
	if (inputs[0].checked) {
		alert('v ' + inputs[0].value);
		PF('canH1').jq.show();
		$('#canH').show();
	}
	else{
		alert('v2 ' + inputs[1].value);
	}
//    for (var i = 0; i < inputs.length; i++) {
//        if (inputs[i].checked) {
//        	alert('v ' + inputs[i].value);
//        }
//    }
	
	
}

function sumaTeorico() {
	
	if(PF('espesor').getValue() == null || PF('espesor').getValue() == 0
			|| PF('largo').getValue() == null || PF('largo').getValue() == 0
			|| PF('ancho').getValue() == null || PF('ancho').getValue() == 0){
			
		PF('unidad').setValue(0);			
	}
	else if(PF('unidad').getValue() > 0 ){
		//alert(PF('unidad').getValue());
		var t = PF('espesor').getValue() * PF('largo').getValue() * PF('ancho').getValue()* PF('unidad').getValue() * 0.00000785; 
		//alert(t);
		PF('teorico').setValue(t);
	}
	
//	alert(PF('largo').getValue());
//	alert(PF('ancho').getValue());
//	alert(PF('unidad').getValue());
//	var t = PF('espesor').getValue() * PF('largo').getValue() * PF('ancho').getValue()* PF('unidad').getValue() * 0.00000785; 
//	PF('teorico').setValue(t);
	
		
}

PrimeFaces.locales['es'] = {
        closeText: 'Cerrar',
        prevText: 'Anterior',
        nextText: 'Siguiente',
        monthNames: ['Enero','Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun','Jul','Ago','Sep','Oct','Nov','Dic'],
        dayNames: ['Domingo','Lunes','Martes','Miércoles','Jueves','Viernes','Sábado'],
        dayNamesShort: ['Dom','Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
        dayNamesMin: ['D','L','M','M','J','V','S'],
        weekHeader: 'Semana',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        timeOnlyTitle: 'Sólo hora',
        timeText: 'Tiempo',
        hourText: 'Hora',
        minuteText: 'Minuto',
        secondText: 'Segundo',
        currentText: 'Fecha actual',
        ampm: false,
        month: 'Mes',
        week: 'Semana',
        day: 'Día',
        allDayText : 'Todo el día'
};


