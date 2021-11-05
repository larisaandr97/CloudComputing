jQuery(function ($) {

    let classes = ['star1', 'star2', 'star3', 'star4', 'star5'];
    let ratings = document.getElementsByClassName("ratings");

    for (let i = 0; i < ratings.length; i++) {
        let currRating = ratings[i].value;
        console.log("Rating:" + currRating);
        for (let j = 0; j < currRating; j++) {
            $('.' + classes[j])[i].classList.remove("bi-star");
            $('.' + classes[j])[i].classList.add("bi-star-fill");
        }
    }

});