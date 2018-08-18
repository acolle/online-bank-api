/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function getUsername() {
    
    var username = document.getElementById("user-name");
    
    var request = new XMLHttpRequest();
    
    request.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 200) {
             alert(this.responseText);
         }
    };
    
    request.open("GET", "/user", true);
    request.responseType = 'text';
//    request.setRequestHeader("Content-type", "application/json");

    request.onload = function() {
        username.textContent = request.response;
};

    request.send();
}

