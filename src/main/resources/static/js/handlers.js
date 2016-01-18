/**
 * Created by CoFFeeBaker on 04.12.2015.
 */

var portFolioArray;
var transArray;
var currentName;
var currentAnzahl;

function portfolio() {

    $.ajax({
        url: "/port", success: function (result) {
            portFolioArray = result;
            Portfolio(portFolioArray);
        }
    });

    $.ajax({
        url: "/trans", success: function (result) {
            transArray = result;
            Transaktionen(transArray);
        }
    });

};

function Portfolio(arr) {
   var out = "<h3>Portfolio</h3> ";
    out += "<table><thead><tr> <td>Namenssymbol</td><td> Menge </td><td> Preis </td><td>Preis aller Aktien</td><td>Verkaufen</td></thead></tr>";
    var i;
    for (i = 0; i < arr.length; i++) {
    out += "<tr> <td>" + arr[i].name + "</td><td>" + arr[i].anzahl + "</td><td>" + arr[i].preis + "</td><td>"+ arr[i].allPreis +"</td><td><button  class='waves-effect waves-light btn red' onclick='readyVerkauf(" + arr[i].key +" ,"+ i +")'>Verkaufen</button></td></tr>";
    }
    out += "</table>"
    document.getElementById("portfolioTabelle").innerHTML = out;
}

function Transaktionen(arr) {
    var out = "<tr><thead> <td>Namenssymbol</td><td> Datum </td><td> Anzahl </td><td>Kapital</td><td>Aktion</td></thead></tr>";
    var i;
    for (i = 0; i < arr.length; i++) {
        out += "<tr> <td>" + arr[i].name + "</td><td>" + arr[i].datum + "</td><td>" + arr[i].anzahl + "</td><td> " + arr[i].kapital + "</td><td>" + arr[i].aktion + "</td></tr>";
    }
    document.getElementById("transaktionenTabelle").innerHTML = out;
}

function readyVerkauf(e,i) {




    document.getElementById("titelModal").innerHTML = portFolioArray[i].name + " verkaufen ? ";
    document.getElementById("verkaufen").innerHTML = "<div class='input-field'><input type='number' id='menge' name='anzahl' title='Anzahl' min='1' max='" + portFolioArray[i].anzahl + "'/><label for='menge'>Anzahl</label></div>"
        + "<input type='hidden'  name='id' value='" + e + "'/>"
        + "<input type='hidden' name='aktienname' value='"+ portFolioArray[i].name +"'/>"
        + "<input type='hidden' name='typ' value='VERKAUFEN'/>";


    currentName = portFolioArray[i].name;





    // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
    $('#Modal').openModal();



}
function saveAnzahl(){
    currentAnzahl = document.getElementById("menge").value;
}

function saveInput(){
    currentAnzahl = document.getElementById("zahli").value;
    currentName = document.getElementById("namei").value;
}

function saveTransaktion(){
    $.ajax({
        url: "/savetrans?aktienname="+ currentName +"&anzahl=" + currentAnzahl + "&typ=VERKAUFEN", success: function (result) {
            console.log(result);
        }
    });
}

function savePositiveTransaktion(){
    $.ajax({
        url: "/savetrans?aktienname="+ currentName +"&anzahl=" + currentAnzahl + "&typ=KAUFEN", success: function (result) {
            console.log(result);
        }
    });
}


function closeModal() {

    $('#verkaufModal').closeModal();
}

function logout(){
    window.location.href = "/logout";
}







