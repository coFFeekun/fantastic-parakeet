/**
 * Created by CoFFeeBaker on 04.12.2015.
 */
window.onload = function portfolio(){

   // var xmlhttp = new XMLHttpRequest();
   // var url = "localhost:8080/port";
   //
   // xmlhttp.open("GET", url, true);
   // xmlhttp.send();
   //
   // //var myArr = JSON.parse(xmlhttp.responseText);
   //alert(xmlhttp.responseText);

    $.ajax({url: "/port", success: function(result){
             myFunction(result);
    }
    });

};

function myFunction(arr) {
    var out = "<tr> <td>Namenssymbol</td><td> Menge </td><td> Kaufpreis </td><td> Kaufdatum </td></tr>";
    var i;
    for (i = 0; i < arr.length; i++) {
        out += "<tr> <td>" + arr[i].name + "</td><td>" + arr[i].menge + "</td><td>" + arr[i].kaufprice + "</td><td>" + arr[i].kaufdatum + "</td></tr>";
    }
    document.getElementById("portfolioTabelle").innerHTML = out;
}


