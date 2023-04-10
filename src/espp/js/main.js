$(function() {
  $("#sub").click(function () {
    // Create POST url
    // adding params, optionally
    var u = new URL('http://localhost:8085/espp/tax');
      u.searchParams.append('contribution', $('#form_id input[name="contribution"]').val());
      u.searchParams.append('startPrice', $('#form_id input[name="startPrice"]').val());
      u.searchParams.append('endPrice', $('#form_id input[name="endPrice"]').val());
      u.searchParams.append('endDate', $('#form_id input[name="endDate"]').val());

    // Display the URL, for diagnostics
    $("#out").text(u);

    // Do HTTP-POST to Webhook
    // includes serialized form data
    $.post({ // $.ajax({ could also work
       url: u,
       data: $('#sub').serialize(),
       success: function(response) {
          // Display response
        //  $('#form_id').text(response);
		document.getElementById('currency').innerHTML = 'Currency: ' +response.currency;
		document.getElementById('taxableAmount').innerHTML = 'Taxable Amount: ' +response.taxableAmount;
		document.getElementById('taxes').innerHTML = 'Taxes: ' +response.taxes;
       }
    });

  });
  
});