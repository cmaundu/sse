function showhidenotes(element) {
    divel = element.parentElement;
    if (element.checked) {
        divel.childNodes[4].style.display = "block";
    } else {
        divel.childNodes[4].style.display = "none";
    }
}

function post(path, params, method) {
    method = method || "post"; // Set method to post by default if not specified.

    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for (var key in params) {
        if (params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
        }
    }

    document.body.appendChild(form);
    form.submit();
}

function newUserForm() {
    var xhttp;
    xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("sart-admin-form-content").innerHTML = this.responseText;
        }
    };

    xhttp.open("GET", "roleForm", true);
    xhttp.send();
}

function editForm(type, rowID) {
    var xhttp;
    xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("sart-admin-form-content").innerHTML = this.responseText;
        }
    };
    switch (type) {
        case 'ROLE': //User Form
            xhttp.open("GET", "roleForm?ID=" + rowID, true);
            break;
        case 'USER': //User Form
            xhttp.open("GET", "userForm?ID=" + rowID, true);
            break;
        case 'PASSWORD': //User Form
            xhttp.open("GET", "passwordForm?ID=" + rowID, true);
            break;
        case 'PATIENT': //User Form
            xhttp.open("GET", "new_patient?ID=" + rowID, true);
            break;
    }
    xhttp.send();
}

function closeform() {
    document.getElementById("sart-admin-form-content").innerHTML = '';
    window.location = window.location.href;
}



function calculatePermissions(perm, comp) {

    var val = Math.pow(2, parseInt(comp.value)); //parseInt(comp.value) * parseInt(comp.value);

    switch (perm) {
        case 'VIEW':
            var viewperm = document.getElementById("viewPermissions").value;

            if (comp.checked && (parseInt(viewperm) & val) == 0) {
                document.getElementById("viewPermissions").value = parseInt(viewperm) + val;
            } else if (!comp.checked && (parseInt(viewperm) & val) > 0) {
                document.getElementById("viewPermissions").value = parseInt(viewperm) - val;
            }
            break;
        case 'ADD':
            var viewperm = document.getElementById("addPermissions").value;

            if (comp.checked && (parseInt(viewperm) & val) == 0) {
                document.getElementById("addPermissions").value = parseInt(viewperm) + val;
            } else if (!comp.checked && (parseInt(viewperm) & val) > 0) {
                document.getElementById("addPermissions").value = parseInt(viewperm) - val;
            }
            break;
        case 'EDIT':
            var viewperm = document.getElementById("editPermissions").value;

            if (comp.checked && (parseInt(viewperm) & val) == 0) {
                document.getElementById("editPermissions").value = parseInt(viewperm) + val;
            } else if (!comp.checked && (parseInt(viewperm) & val) > 0) {
                document.getElementById("editPermissions").value = parseInt(viewperm) - val;
            }
            break;
        case 'DEL':
            var viewperm = document.getElementById("deletePermissions").value;

            if (comp.checked && (parseInt(viewperm) & val) == 0) {
                document.getElementById("deletePermissions").value = parseInt(viewperm) + val;
            } else if (!comp.checked && (parseInt(viewperm) & val) > 0) {
                document.getElementById("deletePermissions").value = parseInt(viewperm) - val;
            }
            break;
    }
}


function scorePassword(pass) {
    var score = 0;
    if (!pass)
        return score;

    // award every unique letter until 5 repetitions
    var letters = new Object();
    for (var i = 0; i < pass.length; i++) {
        letters[pass[i]] = (letters[pass[i]] || 0) + 1;
        score += 5.0 / letters[pass[i]];
    }

    // bonus points for mixing it up
    var variations = {
        digits: /\d/.test(pass),
        lower: /[a-z]/.test(pass),
        upper: /[A-Z]/.test(pass),
        nonWords: /\W/.test(pass),
    }

    variationCount = 0;
    for (var check in variations) {
        variationCount += (variations[check] == true) ? 1 : 0;
    }
    score += (variationCount - 1) * 10;

    return parseInt(score);
}

function checkPassStrength(pass) {

    var score = scorePassword(pass);

    if (score > 60)
        document.getElementById("passstrength").className = "good-password";
    else if (score > 40)
        document.getElementById("passstrength").className = "fair-password";
    else
        document.getElementById("passstrength").className = "bad-password";


    return "";
}

function validatepassword() {
    var psswd = document.getElementById("password");
    var cpsswd = document.getElementById("cpassword");

    if (psswd.value != cpsswd.value) {
        cpsswd.setCustomValidity("Passwords Don't Match");
        return false;
    } else {
        cpsswd.setCustomValidity('');
    }
    var score = scorePassword(psswd.value);

    if (score < 50) {
        psswd.setCustomValidity("Password too simple, please specify a more complex password.");
        return false;
    } else {
        psswd.setCustomValidity('');
    }

    return true;
}


function searchicd(str, url) {
    var xhttp;
    //alert(str+attid);
    document.getElementById("diag_code").value = "";
    if (str.length == 0) {
        document.getElementById("diag-icd-lookup").innerHTML = "";
        return;
    }
    xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("diag-icd-lookup").innerHTML = this.responseText;
        }
    };
    xhttp.open("GET", "" + url + str, true);
    //
    xhttp.send();
}


function addicd(icd_code, icd_short_desc) {
    document.getElementById("description").value = icd_short_desc;
    document.getElementById("diag_code").value = icd_code;
    document.getElementById("diag-icd-lookup").innerHTML = "";
}
function selectdrug(rowID, prescription) {
    document.getElementById("prescription").value = prescription;
    document.getElementById("drugID").value = rowID;
    document.getElementById("diag-drug-lookup").innerHTML = "";
}

function searchdrug(str, url) {
    var xhttp;
    //alert(str+attid);
    document.getElementById("drugID").value = "";
    if (str.length == 0) {
        document.getElementById("diag-drug-lookup").innerHTML = "";
        return;
    }
    xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("diag-drug-lookup").innerHTML = this.responseText;
        }
    };
    xhttp.open("GET", "" + url + str, true);
    //
    xhttp.send();
}


function showrequestview(requestID, url) {
    var xhttp;

    if (requestID.length == 0) {
        document.getElementById("request-modal-form").innerHTML = "";
        document.getElementById("request-modal-form").style = "display:none";
        return;
    }

    xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("request-modal-form").innerHTML = this.responseText;
            document.getElementById("request-modal-form").style = "display:block"
        }
    };
    xhttp.open("GET", "" + url, true);
    //
    xhttp.send();
}

function closerequestview() {
    document.getElementById("request-modal-form").innerHTML = "";
    document.getElementById("request-modal-form").style = "display:none";
}




