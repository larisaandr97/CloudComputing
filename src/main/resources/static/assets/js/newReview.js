jQuery(function ($) {

    let list = ['one', 'two', 'three', 'four', 'five'];
    list.forEach(function (element, index) {
        document.getElementById(element).addEventListener("click", function () {
            let cls = document.getElementById(element).className;
            if (cls.includes("bi-star")) {
                for (let i = 0; i <= index; i++) {
                    document.getElementById(list[i]).classList.remove("bi-star");
                    document.getElementById(list[i]).classList.add("bi-star-fill");
                }
                for (let i = index + 1; i <= 4; i++) {
                    document.getElementById(list[i]).classList.remove("bi-star-fill");
                    document.getElementById(list[i]).classList.add("bi-star");
                }
            } else {
                for (let i = 0; i <= index; i++) {
                    document.getElementById(list[i]).classList.remove("bi-star-fill");
                    document.getElementById(list[i]).classList.add("bi-star");
                }
                for (let i = index + 1; i <= 4; i++) {
                    document.getElementById(list[i]).classList.remove("bi-star");
                    document.getElementById(list[i]).classList.add("bi-star-fill");
                }
            }
            $('#rating').val(index + 1);
        });
    });
});