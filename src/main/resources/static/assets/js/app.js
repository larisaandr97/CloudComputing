jQuery(function ($) {


    let inputs = $('input[name="quantity"]');

    $('.quantity').on('input', function () {

        let index = inputs.index(this);

        let row = $('tr[id^=' + this.id + ']');

        let price = row.find("td:nth-child(4)").text();

        let quantity = $(this).val();
        let modified = row.find("td:nth-child(5)").text(price * quantity);

        // Compute total sum
        let sum = 0.0;
        $('#items tbody tr').each(function (index, tr) {
            sum += parseFloat($(this).find("td:nth-child(5)").text());
        });
        $('#totalAmount').text("Total amount: " + sum);
    });

    //rating productDetails page
    let rating = $('#ratingProduct').val();
    let list2 = ['one1', 'two2', 'three3', 'four4', 'five5'];
    for (let i = 0; i < rating; i++) {
        document.getElementById(list2[i]).classList.remove("bi-star");
        document.getElementById(list2[i]).classList.add("bi-star-fill");
    }

    let sameUser = $('#sameUser').val();
    if (sameUser === 'YES') {
        $('#myToast').toast('show');
    }

});
