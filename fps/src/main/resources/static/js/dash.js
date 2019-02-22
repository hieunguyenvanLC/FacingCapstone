$(document).ready(function () {
    // $('body').bootstrapMaterialDesign();
    var accountTable = $('#account-table').DataTable({
        "dom": "<'row'<'col-sm-12 col-md-3'l><'col-sm-12 col-md-9'f<'filter-group status-group'>>>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
        fnInitComplete: function(){
            $('div.status-group').html('<label>Status</label>\n' +
                '<select class="form-control">\n' +
                '  <option>Any</option>\n' +
                '  <option>Delivered</option>\n' +
                '  <option>Shipped</option>\n' +
                '  <option>Pending</option>\n' +
                '  <option>Cancelled</option>\n' +
                '</select>');
        }
    });

    $('#account-table tbody').on('click', 'tr', function () {
        $(this).toggleClass('selected');
        $("#delete-sel").text("Delete (" + accountTable.rows('.selected').data().length + ")");
        $("#deactive-sel").text("Deactive (" + accountTable.rows('.selected').data().length + ")");
    });

    var orderDetailTable = $("#order-detail-table").DataTable({});
});