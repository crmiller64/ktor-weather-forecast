const doValidation = function (event) {
    if (!this.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
    }

    this.classList.add("was-validated");
}

// Example starter JavaScript for disabling form submissions if there are invalid fields
const addFormValidation = () => {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    const forms = document.querySelectorAll(".validated-form");

    // Loop over them and prevent submission
    Array.from(forms).forEach(function (form) {
        form.removeEventListener("submit", doValidation, false);
        form.addEventListener("submit", doValidation, false);
    });
}

export default addFormValidation;