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
            // console.log(response.data);
            var summary = response.data;
            $("#lbActivedShipper").html("<strong>" + summary.shipperCount + "</strong>");
            $("#lbNewCus").html("<strong>" + summary.CustomerCount + "</strong>");
            $("#lbNewStores").html("<strong>" + summary.StoreCount + "</strong>");
            $("#lbNewOrder").html("<strong>" + summary.OrderCount + "</strong>");
        },
        error: function (err) {
            console.log(err);
        }
    });

    var BARCHARTEXMPLE = $('#barChartExample');
    // barChartExample = new Chart(BARCHARTEXMPLE, {
    //     type: 'bar',
    //     options: {
    //         scales: {
    //             xAxes: [{
    //                 display: true,
    //                 gridLines: {
    //                     color: '#eee'
    //                 }
    //             }],
    //             yAxes: [{
    //                 display: true,
    //                 gridLines: {
    //                     color: '#eee'
    //                 }
    //             }]
    //         },
    //         onClick: handleBarClick
    //     },
    //     data: {
    //         labels: summary.labels,
    //         datasets: [
    //             {
    //                 label: "Orders",
    //                 backgroundColor: 'rgba(255, 119, 119, 0.94)',
    //                 hoverBackgroundColor: 'rgba(255, 119, 119, 0.94)',
    //                 borderColor: 'rgba(255, 119, 119, 0.94)',
    //                 borderWidth: 1,
    //                 data: summary.orders,
    //             },
    //             {
    //                 label: "Canceled Orders",
    //                 backgroundColor: 'rgba(76, 162, 205, 0.85)',
    //                 hoverBackgroundColor: 'rgba(76, 162, 205, 0.85)',
    //                 borderColor: 'rgba(76, 162, 205, 0.85)',
    //                 borderWidth: 1,
    //                 data: summary.canceledOrders,
    //             },
    //             {
    //                 label: "Success Orders",
    //                 backgroundColor: '#3eb579',
    //                 hoverBackgroundColor: '#3eb579',
    //                 borderColor: '#3eb579',
    //                 borderWidth: 1,
    //                 data: summary.successOrders,
    //             }
    //         ]
    //     }
    // });
    barChartExample = new Chart(BARCHARTEXMPLE, {
        type: 'bar',
        options:
            {
                scales:
                    {
                        xAxes: [{
                            display: true
                        }],
                        yAxes: [{
                            display: true
                        }],
                    },
                legend: {
                    display: false
                },
                onClick: handleBarClick
            },
        data: {
            labels: [],
            datasets: [
                // {
                //     label: "Data Set 1",
                //     backgroundColor: [
                //         '#44b2d7',
                //         '#44b2d7',
                //         '#44b2d7',
                //         '#44b2d7',
                //         '#44b2d7',
                //         '#44b2d7',
                //         '#44b2d7',
                //         '#44b2d7'
                //     ],
                //     borderColor: [
                //         '#44b2d7',
                //         '#44b2d7',
                //         '#44b2d7',
                //         '#44b2d7',
                //         '#44b2d7',
                //         '#44b2d7',
                //         '#44b2d7',
                //         '#44b2d7'
                //     ],
                //     borderWidth: 0,
                //     data: [35, 55, 65, 85, 30, 22, 18, 35]
                // },
                // {
                //     label: "Data Set 1",
                //     backgroundColor: [
                //         '#59c2e6',
                //         '#59c2e6',
                //         '#59c2e6',
                //         '#59c2e6',
                //         '#59c2e6',
                //         '#59c2e6',
                //         '#59c2e6',
                //         '#59c2e6'
                //     ],
                //     borderColor: [
                //         '#59c2e6',
                //         '#59c2e6',
                //         '#59c2e6',
                //         '#59c2e6',
                //         '#59c2e6',
                //         '#59c2e6',
                //         '#59c2e6',
                //         '#59c2e6'
                //     ],
                //     borderWidth: 0,
                //     data: [49, 68, 85, 40, 27, 35, 20, 25]
                // }
            ]
        }
    })
    // Xu ly filter
    var reportType = $("#selReportType").val();
    var chartType = $("#selChartType").val();
    var startDate = null;
    var endDate = null;
    $("#selReportType").change(function () {
        reportType = $("#selReportType").val();
        loadChart(reportType, chartType, startDate, endDate);
    });
    $("#selChartType").change(function () {
        chartType = $("#selChartType").val();
        loadChart(reportType, chartType, startDate, endDate);
    });
    var dpkStart = $('#dpkStart').datepicker({
        uiLibrary: 'bootstrap4',
        format: 'dd/mm/yyyy',
        change: function (e) {
            startDate = dpkStart.value();
            loadChart(reportType, chartType, startDate, endDate);
        }
    });
    var dpkEnd = $('#dpkEnd').datepicker({
        uiLibrary: 'bootstrap4',
        format: 'dd/mm/yyyy',
        change: function (e) {
            endDate = dpkEnd.value();
            loadChart(reportType, chartType, startDate, endDate);
        }
    });

    function loadChart(reportT, charT, start, end) {
        if (!start || !end) {
            return;
        }

        var apiEndpoint = "orderchart";
        var chartTitle = "Orders";
        var startUnix = moment(start, "DD/MM/YYYY").unix() * 1000;
        var endUnix = moment(end, "DD/MM/YYYY").unix() * 1000;
        console.log(reportT, charT, startUnix, endUnix);

        switch (reportT) {
            case "orderCnl":
                apiEndpoint = "canceledorderchart";
                chartTitle = "Canceled Orders";
                break;
            case "orderScc":
                apiEndpoint = "successorderchart";
                chartTitle = "Success Orders";
                break;
            case "productSld":
                apiEndpoint = "soldproductchart";
                chartTitle = "Sold Products";
                break;
            case "rateScc":
                apiEndpoint = "successratechart";
                chartTitle = "Success Delivery Rate";
                break;
            case "totalAmt":
                apiEndpoint = "incomeammountchart";
                chartTitle = "Total Amount";
                break;
            case "shipPaid":
                apiEndpoint = "paidshipperchart";
                chartTitle = "Paid Shipper Amount";
                break;
            default:
                apiEndpoint = "orderchart";
                chartTitle = "Orders";
        }

        $.ajax({
            url: "/any/api/report/" + apiEndpoint + "?type=" + charT + "&start=" + startUnix + "&end=" + endUnix,
            type: "GET",
            dataType: "json",
            success: function (response) {
                // console.log(response.data);
                var chartData = response.data;

                barChartExample.data.labels = chartData.labels;
                barChartExample.data.datasets = [{
                    label: chartTitle,
                    backgroundColor: '#44b2d7',
                    borderColor: '#44b2d7',
                    borderWidth: 0,
                    data: chartData.data
                }];
                barChartExample.update();
            },
            error: function (err) {
                console.log(err);
            }
        });

    }

    function handleBarClick(evt) {
        var activeElement = barChartExample.getElementAtEvent(evt);

        if (!activeElement.length) {
            //     $("#valOrderCnt").html("--");
            //     $("#valCanceledOrderCnt").html("--");
            //     $("#valSuccessOrderCnt").html("--");
            //     $("#valRateOrder").html("--");
            //     $("#valSoldProductCnt").html("--");
            return;
        }

        var selectedBar = activeElement[0]._model.label;
        var status = 1;
        var startUnix1 = 0;
        var endUnix1 = 0;

        switch (reportType) {
            case "orderCnl":
                status = 5;
                break;
            case "orderScc":
                status = 4;
                break;
            default:
                status = 1;
        }

        switch (chartType) {
            case '0':
                // la ngay
                startMoment = moment(startDate, "DD/MM/YYYY");
                startUnix1 = startMoment.clone().date(parseInt(selectedBar)).unix() * 1000;
                endUnix1 = startMoment.clone().date(parseInt(selectedBar) + 1).unix() * 1000;
                break;

            case '1':
                break;

            case '2':
                startMoment = moment(startDate, "DD/MM/YYYY");
                startUnix1 = startMoment.clone().date(1).month(parseInt(selectedBar)).unix() * 1000;
                endUnix1 = startMoment.clone().date(1).month(parseInt(selectedBar) + 1).unix() * 1000;
                break;

            case '3':
                startUnix1 = moment("01/01/" + selectedBar, "DD/MM/YYYY").unix() * 1000;
                endUnix1 = moment("01/01/" + (parseInt(selectedBar) + 1), "DD/MM/YYYY").unix() * 1000;
                break;
        }

        console.log(reportType, chartType, startUnix1, endUnix1);
        // console.log(day);
        $.ajax({
            url: "/any/api/report/orderlist?status=" + status + "&start=" + startUnix1 + "&end=" + endUnix1,
            type: "GET",
            dataType: "json",
            success: function (response) {
                var orders = response.data;
                console.log(orders);
            },
            error: function (err) {
                console.log(err);
            }
        });
    }

    // Show chart
    // loadChart(reportType, chartType, 0, 0);
});

function fpsFormatDate(date) {
    var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    var day = date.getDate();
    var monthIndex = date.getMonth();
    var year = date.getFullYear();
    return day + ' ' + monthNames[monthIndex] + ' ' + year;
}

function changeTimeWeek() {
    var _initial = new Date(),
        _initial = _initial.setDate(_initial.getDate()),
        _final = new Date(_initial);
    _final = _final.setDate(_final.getDate() + 15 / 1000 * 60);

    var dif = Math.round((_final - _initial) / (1000 * 60));
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
