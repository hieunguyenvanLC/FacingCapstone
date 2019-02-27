var storeTable = null;
var shipperTable = null;

$(document).ready(function () {
    // $('body').bootstrapMaterialDesign();
    var accountTable = $('#account-table').DataTable({
        "dom": "<'row'<'col-sm-12 col-md-3'l><'col-sm-12 col-md-9'f<'filter-group status-group'><'filter-group active-group'>>>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
        fnInitComplete: function () {
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
        // $("#delete-sel").text("Delete (" + accountTable.rows('.selected').data().length + ")");
        // $("#deactive-sel").text("Deactive (" + accountTable.rows('.selected').data().length + ")");
        // $("#cancel-sel").text("Cancel (" + accountTable.rows('.selected').data().length + ")");
    });

    var orderDetailTable = $("#order-detail-table").DataTable({});

    //Shipper table Get Data
    shipperTable = $('#shipper-table').DataTable({
        "dom": "<'row'<'col-sm-12 col-md-3'l><'col-sm-12 col-md-9'f<'filter-group status-group'><'filter-group active-group'>>>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
        fnInitComplete: function () {
            $('div.status-group').html('<label>Status</label>\n' +
                '<select class="form-control">\n' +
                '  <option>Any</option>\n' +
                '  <option>Delivered</option>\n' +
                '  <option>Shipped</option>\n' +
                '  <option>Pending</option>\n' +
                '  <option>Cancelled</option>\n' +
                '</select>');


            $('#shipper-table tbody button').click(function (ev) {
                ev.stopPropagation(); // khong cho dialog hien len
            });

            $('#shipper-table tbody tr').click(function (event) {
                $('#shipper-table tbody tr').toggleClass('selected');
                event.preventDefault();
                jQuery.noConflict();
                var data = shipperTable.row(row).data();
                $('#add-edit-modal-shipper').modal('show');
            });

        }
    });


    // }, 'createdRow': function (row, data, dataIndex) {
    //     $(row).click(function (event) {
    //         $(row).toggleClass('selected');
    //         event.preventDefault();
    //         jQuery.noConflict();
    //         var data = storeTable.row(row).data();
    //         $('#add-edit-modal-shipper').modal('show');
    //     });
    // }, "data": null,
    // "columnDefs": [
    //     {"targets": 0, "data": "id"},
    //     {"targets": 1, "data": "name"},
    //     {"targets": 2, "data": "address"},
    //     {"targets": 3, "data": "rating"},
    //     {"targets": 4, "data": "status"},
    //     {
    //         "targets": 5,
    //         "data": null,
    //         "defaultContent": "<button class=\"btn btn-danger\">Deactive</button>"
    //     }
    // ]


// Store table Get Data
    $.ajax({
        url: "/any/api/stores",
        type: "GET",
        dataType: "json",
        success: function (response) {
            // console.log(response);
            storeTable = $('#store-table').DataTable({
                "dom": "<'row'<'col-sm-12 col-md-3'l><'col-sm-12 col-md-9'f<'filter-group status-group'><'filter-group active-group'>>>" +
                    "<'row'<'col-sm-12'tr>>" +
                    "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
                fnInitComplete: function () {
                    $('div.status-group').html('<label>Status</label>\n' +
                        '<select class="form-control">\n' +
                        '  <option>Any</option>\n' +
                        '  <option>Delivered</option>\n' +
                        '  <option>Shipped</option>\n' +
                        '  <option>Pending</option>\n' +
                        '  <option>Cancelled</option>\n' +
                        '</select>');

                    $('#store-table tbody button').click(function (ev) {
                        ev.stopPropagation(); // khong cho dialog hien len
                        var data = storeTable.row($(this).parents('tr')).data();
                        //  console.log(data);
                        // https://stackoverflow.com/questions/5963669/whats-the-difference-between-event-stoppropagation-and-event-preventdefault
                        // goi api deactive store o day
                    });
                },
                'createdRow': function (row, data, dataIndex) {
                    // $(row).attr('data-target', '#store-detail-modal');
                    // $(row).attr('data-toggle', 'modal');
                    $(row).click(function (event) {
                        $(row).toggleClass('selected');
                        // https://stackoverflow.com/questions/27064176/typeerror-modal-is-not-a-function-with-bootstrap-modal/28173513
                        event.preventDefault();
                        jQuery.noConflict();

                        var data = storeTable.row(row).data();
                        console.log(data);
                        $('#store-detail-modal').modal('show');
                    });
                },
                "data": response.data,
                "columnDefs": [
                    {"targets": 0, "data": "id"},
                    {"targets": 1, "data": "name"},
                    {"targets": 2, "data": "address"},
                    {"targets": 3, "data": "rating"},
                    {"targets": 4, "data": "status"},
                    {
                        "targets": 5,
                        "data": null,
                        "defaultContent": "<button class=\"btn btn-danger\">Deactive</button>"
                    }
                ]
            });
        },
        error: function (err) {
            console.log(err);
        }
    });
})
;

