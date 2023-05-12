let modal = document.getElementById("modal");
let span = document.getElementsByClassName("close")[0];

modal.style.display = "block";

span.onclick = function() {
    window.location.href = "/dashboard";
}

window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}