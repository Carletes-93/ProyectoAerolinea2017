function ida(){
    var texto = document.getElementById('texto').innerHTML="Solo ida";
    var fechavuelta = document.getElementById('fechavuelta');
    var boton = document.getElementById('accion');
        boton.value="IDA";
        fechavuelta.disabled = true;
        fechavuelta.required = false;
        
    var btn_vuelta = document.getElementById('vuelta');
        btn_vuelta.disabled = false;
        btn_vuelta.removeAttribute('class');
        btn_vuelta.setAttribute('class', 'activado');
        
    var btn_ida = document.getElementById('ida');
        btn_ida.disabled = true;
        btn_ida.removeAttribute('class');
        btn_ida.setAttribute('class', 'desactivado');
}

function vuelta(){
    var texto = document.getElementById('texto').innerHTML="Ida y vuelta";
    var fechavuelta = document.getElementById('fechavuelta');
    var boton = document.getElementById('accion');
    boton.value="VUELTA";
        fechavuelta.disabled = false;
        fechavuelta.required = true;
    
    var btn_ida = document.getElementById('ida');
        btn_ida.disabled = false;
        btn_ida.removeAttribute('class');
        btn_ida.setAttribute('class', 'activado');
    
    var btn_vuelta = document.getElementById('vuelta');
        btn_vuelta.disabled = true;
        btn_vuelta.removeAttribute('class');
        btn_vuelta.setAttribute('class', 'desactivado');
}

//Funcion Datepicker
$(function() {
    $.datepicker.setDefaults($.datepicker.regional["es"]);
    $( ".datepickerida" ).datepicker({
        showButtonPanel: true,
        changeMonth: true,
        changeYear: true,
        minDate: 0}
        );
  });

$(function() {
    $.datepicker.setDefaults($.datepicker.regional["es"]);
    $( ".datepickervuelta" ).datepicker({
        showButtonPanel: true,
        changeMonth: true,
        changeYear: true,
        minDate: "+1d"}
        );
  });
  
  $(function() {
    $.datepicker.setDefaults($.datepicker.regional["es"]);
    $( ".datepickernac" ).datepicker({
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true}
        );
  });
  
  $(function() {
    $.datepicker.setDefaults($.datepicker.regional["es"]);
    $( ".datepickerbebe" ).datepicker({
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true}
        );
  });
  
  $(function() {
    $.datepicker.setDefaults($.datepicker.regional["es"]);
    $( ".pagadordatepicker" ).datepicker({
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true}
        );
  });
  
  $(function() {
    $.datepicker.setDefaults($.datepicker.regional["es"]);
    $( ".tarjetadatepicker" ).datepicker({
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true}
        );
  });
  
  $(function() {
    $.datepicker.setDefaults($.datepicker.regional["es"]);
    $( ".caducidad" ).datepicker({
        showButtonPanel: true,
        changeMonth: true,
        changeYear: true,
        minDate: "+1M"}
        );
  });
  
  
  $(function() {
    $( "#button" ).buttonset();
  });
  
function AJAXCrearObjeto() {
    if (window.XMLHttpRequest) {
        var objetoAjax = new XMLHttpRequest();
    } else {
        var objetoAjax = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return objetoAjax;
}

//Aeropuertos
var aeropuertos = [
    'Madrid',
    'Paris',
    'Berlin',
    'Londres'
];

function cargarPagina(){
    mostrarOrigen();
}

function mostrarOrigen(){
    var objselect1 = document.getElementById('origen');
        objselect1.style.cursor='pointer';
    
    var elemoption = document.createElement('option');
        elemoption.appendChild(document.createTextNode("Origen"));
        elemoption.setAttribute('selected', 'selected');
    objselect1.appendChild(elemoption);
    
    for (var i = 0; i < aeropuertos.length; i++) {
        var elemoption = document.createElement('option');
        elemoption.value = aeropuertos[i];
        elemoption.appendChild(document.createTextNode(aeropuertos[i]));
        objselect1.appendChild(elemoption);
    }
}

function mostrarDestino(){
    var objselect1 = document.getElementById('origen').value;
    var objselect2 = document.getElementById('destino');
    
    while (objselect2.hasChildNodes()) {
        objselect2.removeChild(objselect2.firstChild);
    }
    
    if(objselect1==="Origen"){
        var elemoption = document.createElement('option');
        elemoption.appendChild(document.createTextNode("Destino"));
        objselect2.appendChild(elemoption);
        objselect2.disabled=true;
    }
    else{
        objselect2.disabled=false;
        objselect2.style.cursor='pointer';
        var elemoption = document.createElement('option');
            elemoption.appendChild(document.createTextNode("Destino"));
        objselect2.appendChild(elemoption);
        for (var i = 0; i < aeropuertos.length; i++) {
            if(objselect1!==aeropuertos[i]){
                var elemoption = document.createElement('option');
                elemoption.appendChild(document.createTextNode(aeropuertos[i]));
                objselect2.appendChild(elemoption);
            }
        }
    }
}

function cambiarninos(){
    var objselectadultos = document.getElementById('adultos');
    var seleccion = objselectadultos.selectedIndex;
    var total = objselectadultos.length;
    
    var objselectninos = document.getElementById('ninos');
    
    while (objselectninos.hasChildNodes()) {
        objselectninos.removeChild(objselectninos.firstChild);
    }
    
    for (var i = 0; i < (total-seleccion); i++) {
        var elemoption = document.createElement('option');
            elemoption.appendChild(document.createTextNode(i+" Ni\u00f1os (2-17)"));
            elemoption.value=i;
            objselectninos.appendChild(elemoption);
    }
}

//Funcion tooltips
$(function() {
    $( document ).tooltip();
  });
  
//Servicio bebe
function datosBebe(evento){
    var id = evento;
    var idtr="servt"+id;
    var elem=document.getElementById(id);
    var trbebe=document.getElementById(idtr);
    
    if(elem.checked){
        trbebe.style.display="inline";
    }
    else{
        trbebe.style.display="none";
    }
}

//Acordeones
$(function() {
    $( ".acordeon" ).accordion({
        heightStyle: "content",
        collapsible: true
    });
  });