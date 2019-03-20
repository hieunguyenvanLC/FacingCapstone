var barChartExample;

$(document).ready(function () {
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

    $.ajax({
        url: "/any/api/report/summary?mon=3&year=2019",
        type: "GET",
        dataType: "json",
        success: function (response) {
            console.log(response.data);
            var summary = response.data;
            $("#lbActivedShipper").html("<strong>" + summary.shipperCount + "</strong>");
            $("#lbNewCus").html("<strong>" + summary.CustomerCount + "</strong>");
            $("#lbNewStores").html("<strong>" + summary.StoreCount + "</strong>");
            $("#lbNewOrder").html("<strong>" + summary.OrderCount + "</strong>");

            var BARCHARTEXMPLE = $('#barChartExample');
            barChartExample = new Chart(BARCHARTEXMPLE, {
                type: 'bar',
                options: {
                    scales: {
                        xAxes: [{
                            display: true,
                            gridLines: {
                                color: '#eee'
                            }
                        }],
                        yAxes: [{
                            display: true,
                            gridLines: {
                                color: '#eee'
                            }
                        }]
                    },
                    onClick: handleBarClick
                },
                data: {
                    labels: summary.labels,
                    datasets: [
                        {
                            label: "Orders",
                            backgroundColor: 'rgba(255, 119, 119, 0.94)',
                            hoverBackgroundColor: 'rgba(255, 119, 119, 0.94)',
                            borderColor: 'rgba(255, 119, 119, 0.94)',
                            borderWidth: 1,
                            data: summary.orders,
                        },
                        {
                            label: "Canceled Orders",
                            backgroundColor: 'rgba(76, 162, 205, 0.85)',
                            hoverBackgroundColor: 'rgba(76, 162, 205, 0.85)',
                            borderColor: 'rgba(76, 162, 205, 0.85)',
                            borderWidth: 1,
                            data: summary.canceledOrders,
                        },
                        {
                            label: "Success Orders",
                            backgroundColor: '#3eb579',
                            hoverBackgroundColor: '#3eb579',
                            borderColor: '#3eb579',
                            borderWidth: 1,
                            data: summary.successOrders,
                        }
                    ]
                }
            });
        },
        error: function (err) {
            console.log(err);
        }
    });
});

function handleBarClick(evt) {
    var activeElement = barChartExample.getElementAtEvent(evt);

    if (!activeElement.length) {
        $("#valOrderCnt").html("--");
        $("#valCanceledOrderCnt").html("--");
        $("#valSuccessOrderCnt").html("--");
        $("#valRateOrder").html("--");
        $("#valSoldProductCnt").html("--");
        return;
    }

    var day = activeElement[0]._model.label;

    $.ajax({
        url: "/any/api/report/summaryDetail?year=2019&mon=3&day=" + day,
        type: "GET",
        dataType: "json",
        success: function (response) {
            var detail = response.data;
            console.log(detail);
            $("#valOrderCnt").html(detail.orderCountBy);
            $("#valCanceledOrderCnt").html(detail.orderCancelBy);
            $("#valSuccessOrderCnt").html(detail.orderSuccessBy);
            $("#valRateOrder").html((1.0 * detail.orderSuccessBy / detail.orderCountBy).toFixed(2) + '%');
            $("#valSoldProductCnt").html(detail.soldProductBy);
        },
        error: function (err) {
            console.log(err);
        }
    });
}

function fpsFormatDate(date) {
    var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    var day = date.getDate();
    var monthIndex = date.getMonth();
    var year = date.getFullYear();
    return day + ' ' + monthNames[monthIndex] + ' ' + year;
}


function fpsShowMsg(lblMsg, msgStr) {
    // var lblMsg = document.getElementById("lblDisplayMsg");
    lblMsg.innerHTML = msgStr;
    lblMsg.style.display = 'inline-block';
}

function fpsHideMsg(lblMsg) {
    // var lblMsg = document.getElementById("lblDisplayMsg");
    lblMsg.style.display = 'none';
}

function fpsSetCbbStatus(cbbFpsStat, statList) {
    cbbFpsStat.innerHTML = '';
    for (var i = 0; i < statList.length; i++) {
        var opt = '<option value="' + statList[i].index + '">' + statList[i].msg + '</option>'
        cbbFpsStat.innerHTML += opt;
    }
}

function fpsGetStatMsg(list, index) {
    for (var i = 0; i < list.length; i++) {
        if (index === list[i].index) {
            return list[i].msg;
        }
    }
    return null;
}

function fpsSetImgDefault(img) {
    img.setAttribute("src", defaultImg);
}

function fpsSetImgSrc(img, srcStr) {
    if (typeof srcStr !== 'undefined') {
        img.setAttribute("src", srcStr);
    } else {
        img.setAttribute("src", defaultImg);
    }
}


function fpsSetImgFromInput(img, input) {
    var url = input.value;
    var ext = url.substring(url.lastIndexOf('.') + 1).toLowerCase();
    if (input.files && input.files[0] && (ext === "png" || ext === "jpeg" || ext === "jpg")) {
        var reader = new FileReader();
        reader.readAsDataURL(input.files[0]);
        reader.onload = function () {
            img.setAttribute('src', reader.result);
            return true;
        };
        reader.onerror = function (error) {
            console.log('Error: ', error);
            return false;
        };
    } else {
        return false;
    }
}

function fpsGetBase64(img, file) {
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function () {
        img.setAttribute('src', reader.result);
    };
    reader.onerror = function (error) {
        console.log('Error: ', error);
    };
}

function fpsFormAppend(formData, paramName, paramValue) {
    if (typeof paramValue === 'undefined' || Number.isNaN(paramValue) || paramValue === "") {
        return;
    }
    formData.append(paramName, paramValue);
}

function fpsFormAppend2(formData, paramName, newValue, oldValue) {
    if (typeof newValue === 'undefined' || Number.isNaN(newValue) || newValue === "") {

        return;
    }
    if (typeof oldValue !== 'undefined' && !Number.isNaN(oldValue) && newValue == oldValue) {
        return;
    }
    formData.append(paramName, newValue);
}
