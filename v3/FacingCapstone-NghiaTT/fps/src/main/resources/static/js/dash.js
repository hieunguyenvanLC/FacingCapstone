// $(document).ready(function () {
//     // $('body').bootstrapMaterialDesign();
//     var accountTable = $('#account-table').DataTable({
//         "dom": "<'row'<'col-sm-12 col-md-3'l><'col-sm-12 col-md-9'f<'filter-group status-group'><'filter-group active-group'>>>" +
//             "<'row'<'col-sm-12'tr>>" +
//             "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
//         fnInitComplete: function () {
//             $('div.status-group').html('<label>Status\n' +
//                 '<select class="form-control form-control-sm">\n' +
//                 '  <option>Any</option>\n' +
//                 '  <option>Delivered</option>\n' +
//                 '  <option>Shipped</option>\n' +
//                 '  <option>Pending</option>\n' +
//                 '  <option>Cancelled</option>\n' +
//                 '</select></label>');
//
//         }
//     });
//
//     $('#account-table tbody').on('click', 'tr', function () {
//         $(this).toggleClass('selected');
//         $("#delete-sel").text("Delete (" + accountTable.rows('.selected').data().length + ")");
//         $("#deactive-sel").text("Deactive (" + accountTable.rows('.selected').data().length + ")");
//         $("#cancel-sel").text("Cancel (" + accountTable.rows('.selected').data().length + ")");
//
//     });
//
//     var orderDetailTable = $("#order-detail-table").DataTable({});
// });

var orderTable = null;
var productStoreTable = null;

function fpsFormatDate(date) {
    var monthNames = [
        "Jan", "Feb", "Mar",
        "Apr", "May", "Jun", "Jul",
        "Aug", "Sep", "Oct",
        "Nov", "Dec"
    ];

    var day = date.getDate();
    var monthIndex = date.getMonth();
    var year = date.getFullYear();

    return day + ' ' + monthNames[monthIndex] + ' ' + year;
}


function fpsShowMsg(msgStr) {
    var lblMsg = document.getElementById("lblDisplayMsg");
    lblMsg.innerHTML = msgStr;
    lblMsg.style.display = 'inline-block';
}

function fpsHideMsg() {
    var lblMsg = document.getElementById("lblDisplayMsg");
    lblMsg.style.display = 'none';
}

function fpsGetStatMsg(list, index) {
    for (var i = 0; i < list.length; i++) {
        if (index === list[i].index) {
            return list[i].msg;
        }
    }
    return "";
}

$(document).ready(function () {

    // Order table
    $.ajax({
        url: "/any/api/orders",
        type: "GET",
        dataType: "json",
        success: function (response) {
            // console.log(response);
            response.data = response.data.map(function (item) {
                item.bookTime = moment(item.bookTime).format('DD/MM/YYYY');
                return item;
            });

            orderTable = $("#order-table").DataTable({
                "dom": "<'row'<'col-sm-12 col-md-3'l><'col-sm-12 col-md-9'f<'filter-group status-group'><'filter-group active-group'>>>" +
                    "<'row'<'col-sm-12'tr>>" +
                    "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
                fnInitComplete: function () {
                    // $('div.status-group').html('<label>Status</label>\n' +
                    //     '<select class="form-control">\n' +
                    //     '  <option>Any</option>\n' +
                    //     '  <option>Delivered</option>\n' +
                    //     '  <option>Shipped</option>\n' +
                    //     '  <option>Pending</option>\n' +
                    //     '  <option>Cancelled</option>\n' +
                    //     '</select>');

                    $('#order-table tbody button').click(function (ev) {
                        ev.stopPropagation(); // khong cho dialog hien len
                        var data = orderTable.row($(this).parents('tr')).data();
                        // console.log(data);
                        cancelOrder(data.id, 1);
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
                        var orderData = orderTable.row(row).data();

                        $.ajax({
                            url: "/any/api/order?id=" + orderData.id, // sau dau cham hoi goi la query string
                            type: "GET",
                            dataType: "json",
                            success: function (response1) {
                                console.log(response1);
                                $("#customer-info-text").html("<b>" + response1.data.customer.name + "</b>")
                                // $("#address-info-text").html("<b>" + response1.data.shipper.address +"</b>")
                                // checkStatus = $("#checkStatus").html(response1.data.order.status)


                                //Opop up fix Modal
                                // if ($.fn.dataTable.isDataTable('#order-detail-table')) {
                                //     $('#order-detail-table').DataTable().destroy();
                                // }
                                // // productStoreTable = $('#order-detail-table').DataTable({


                                if (productStoreTable) {
                                    productStoreTable.destroy();
                                }

                                productStoreTable = $('#order-detail-table').DataTable({
                                    "data": response1.data.products,
                                    "columnDefs": [
                                        {"targets": 0, "data": "id"},
                                        {"targets": 1, "data": "productName"},
                                        {"targets": 2, "data": "unitPrice"},
                                        {"targets": 3, "data": "quantity"}
                                    ],
                                    fnInitComplete: function () { // khoi tao datatable hoan tat

                                        // jQuery.noConflict();
                                        $('#order-detail-modal').modal('show');
                                    }
                                });
                            },
                            error: function (err) {
                                console.log(err);
                            }
                        })

                    });
                },

                "data": response.data,
                "columnDefs": [
                    {"targets": 0, "data": "orderCode"},
                    {"targets": 1, "data": "bookTime"},
                    {"targets": 2, "data": "customerName"},
                    {"targets": 3, "data": "shipperName"},
                    {"targets": 4, "data": "totalPrice"},
                    {"targets": 5, "data": "status"},


                    //         ,
                    //         "render": function (data, type, row) {
                    //     if ({data: "status"} === '1') {
                    //         return 'New';
                    //     }
                    // }


                    {
                        "targets": 6,
                        "data": null,
                        "defaultContent": "<button class=\"btn btn-danger\" onclick='' '>Cancel</button>"
                    }
                ]
            });
        },
        error: function (err) {
            console.log(err);
        }
    });

    function cancelOrder(orderId, userId) {
        $.ajax({
            type: 'POST',
            url: "/adm/api/order/cancel",
            dataType: 'json',
            data: {
                id: orderId,
                userId: userId
            },
            success: function (response) {
                console.log(response);
            },
            error: function (err) {
                console.log(err);
            }
        });
    }
});