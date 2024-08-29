/**
 * 
 */


function showPassword() {
	var x = document.getElementById("inputPassword");
	if (x.type === "password") {
		x.type = "text";
	} else {
		x.type = "password";
	}
}

function myFunction() {
	document.getElementById("myDropdown").classList.toggle("show");
}

window.onclick = function(event) {
	if (!event.target.matches('.dropbtn')) {
		var dropdowns = document.getElementsByClassName("dropdown-content");
		var i;
		for (i = 0; i < dropdowns.length; i++) {
			var openDropdown = dropdowns[i];
			if (openDropdown.classList.contains('show')) {
				openDropdown.classList.remove('show');
			}
		}
	}
}

function mostraInput() {
	document.getElementById('input-maglietta').style.display = 'none';
	document.getElementById('input-camicia').style.display = 'none';
	document.getElementById('input-vestito').style.display = 'none';
	document.getElementById('input-pantaloni').style.display = 'none';
	document.getElementById('input-scarpe').style.display = 'none';

	var selezione = document.getElementById('selezione').value;

	if (selezione === 'maglietta') {
		document.getElementById('input-maglietta').style.display = 'block';
	} else if (selezione === 'camicia') {
		document.getElementById('input-camicia').style.display = 'block';
	} else if (selezione === 'vestito') {
		document.getElementById('input-vestito').style.display = 'block';
	} else if (selezione === 'pantaloni') {
		document.getElementById('input-pantaloni').style.display = 'block';
	} else if (selezione === 'scarpe') {
		document.getElementById('input-scarpe').style.display = 'block';
	}
}

