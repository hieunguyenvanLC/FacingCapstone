var barChartExample;
// var reportTable = null;
var uplBuyerFace = document.getElementById("uplBuyerFace");
var imgBuyerFace = document.getElementById("imgBuyerFace");

var uplBill = document.getElementById("uplBill");
var imgBill = document.getElementById("imgBill");

var txtOrderId = document.getElementById("txtOrderId");
var txtBuyerName = document.getElementById("txtBuyerName");
var txtBuyerPhone = document.getElementById("txtBuyerPhone");
var txtShipperName = document.getElementById("txtShipperName");
var txtShipperPhone = document.getElementById("txtShipperPhone");
var cbbStatus = document.getElementById("cbbStatus");

var txtLatitude = document.getElementById("txtLatitude");
var txtLongitude = document.getElementById("txtLongitude");

var txtTotalPrice = document.getElementById("txtTotalPrice");
var txtShipperEarn = document.getElementById("txtShipperEarn");

var txtStoreName = document.getElementById("txtStoreName");
var txtCustomerDescription = document.getElementById("txtCustomerDescription");
var txtNote = document.getElementById("txtNote");

var btnCloseModal = document.getElementById("btnCloseModal");
var tblBodyDetail = document.getElementById("tblBodyDetail");
////
var orderList;
var orderEdit;
var orderEditPos;
var fpsApiOrd = fpsBackEnd + MAP_ADM + MAP_API + "/order";  // /adm/api/order

$(document).ready(function () {
    uplBuyerFace = document.getElementById("uplBuyerFace");
    imgBuyerFace = document.getElementById("imgBuyerFace");

    uplBill = document.getElementById("uplBill");
    imgBill = document.getElementById("imgBill");

    txtOrderId = document.getElementById("txtOrderId");
    txtBuyerName = document.getElementById("txtBuyerName");
    txtBuyerPhone = document.getElementById("txtBuyerPhone");
    txtShipperName = document.getElementById("txtShipperName");
    txtShipperPhone = document.getElementById("txtShipperPhone");
    cbbStatus = document.getElementById("cbbStatus");

    txtLatitude = document.getElementById("txtLatitude");
    txtLongitude = document.getElementById("txtLongitude");

    txtTotalPrice = document.getElementById("txtTotalPrice");
    txtShipperEarn = document.getElementById("txtShipperEarn");

    txtStoreName = document.getElementById("txtStoreName");
    txtCustomerDescription = document.getElementById("txtCustomerDescription");
    txtNote = document.getElementById("txtNote");

    btnCloseModal = document.getElementById("btnCloseModal");
    tblBodyDetail = document.getElementById("tblBodyDetail");
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
    function loadSummary() {
        $.ajax({
            url: "/any/api/report/summary?mon=7&year=2019",
            type: "GET",
            dataType: "json",
            success: function (response) {
                // console.log(response.data);
                var summary = response.data;
                $("#lbActivedShipper").html("<strong>" + summary.shipperCount + "</strong>");
                $("#lbNewCus").html("<strong>" + summary.CustomerCount + "</strong>");
                $("#lbNewStores").html("<strong>" + summary.StoreCount + "</strong>");
                $("#lbNewOrder").html("<strong>" + summary.OrderCount + "</strong>");
                $("#lbSuccessRate").html("<strong>" + summary.successRate + "%</strong>");
                $("#lbSuccessRateD").html("<strong>" + summary.successRateTDay + "%</strong>");
                $("#lbSuccessRateW").html("<strong>" + summary.successRateTWeek + "%</strong>");
                $("#lbSuccessRateM").html("<strong>" + summary.successRateTMonth + "%</strong>");
                $("#lbTotalAmount").html("<strong>" + summary.totalAmount + " VND</strong>");
                $("#lbTotalAmountD").html("<strong>" + summary.totalAmountTDay + " VND</strong>");
                $("#lbTotalAmountW").html("<strong>" + summary.totalAmountTWeek + " VND</strong>");
                $("#lbTotalAmountM").html("<strong>" + summary.totalAmountTMonth + " VND</strong>");
                $("#lbPaidShipper").html("<strong>" + summary.paidShipper + " VND</strong>");
                $("#lbPaidShipperD").html("<strong>" + summary.paidShipperTDay + " VND</strong>");
                $("#lbPaidShipperW").html("<strong>" + summary.paidShipperTWeek + " VND</strong>");
                $("#lbPaidShipperM").html("<strong>" + summary.paidShipperTMonth + " VND</strong>");
                $("#lbSoldProduct").html("<strong>" + summary.soldProductCount + "</strong>");
                $("#lbSoldProductD").html("<strong>" + summary.soldProductCountTDay + "</strong>");
                $("#lbSoldProductW").html("<strong>" + summary.soldProductCountTWeek + "</strong>");
                $("#lbSoldProductM").html("<strong>" + summary.soldProductCountTMonth + "</strong>");

            },
            error: function (err) {
                console.log(err);
            }
        });
    }

    loadSummary();
    setInterval(loadSummary, 5000);

    // Init chart
    var mainDataTable = null;
    var mainChart = null;
    google.charts.load('current', {'packages': ['line']}); // bar => line (doi chart)
    google.charts.setOnLoadCallback(function () {
        mainChart = new google.charts.Line(document.getElementById('barChartExample')); // Bar => Line (doi chart)

        google.visualization.events.addListener(mainChart, 'select', selectHandler);

        function selectHandler(e) {
            // alert('A table row was selected');
            var selections = mainChart.getSelection();
            if (selections.length) {
                var selection = selections[0];
                if (selection.row && selection.column) {
                    // console.log(e, mainDataTable.getValue(selection.row, 0));
                    var status = selection.column;

                    switch (reportType) {
                        case "productSld":
                            status = 4;
                            break;
                        case "rateScc":
                            if (selection.column === 1) {
                                status = 4;
                            } else {
                                status = 5;
                            }
                            break;
                        case "totalAmt":
                            status = 4;
                            break;
                    }

                    handleBarClick(mainDataTable.getValue(selection.row, 0), status);
                }
            }
        }
    });

    // Khoi tao filter
    var selReportType = $("#selReportType");
    var selChartType = $("#selChartType");
    selReportType.dropdown({
        uiLibrary: 'bootstrap4'
    });
    selChartType.dropdown({
        uiLibrary: 'bootstrap4'
    });

    // Xu ly filter
    var reportType = selReportType.val();
    var chartType = selChartType.val();
    var startDate = null;
    var endDate = null;
    selReportType.change(function () {
        reportType = selReportType.val();
        loadChart(reportType, chartType, startDate, endDate);
    });
    selChartType.change(function () {
        chartType = selChartType.val();
        var calendarWeeks = false;

        if (dpkStart) {
            startDate = '';
            dpkStart.destroy();
        }

        if (dpkEnd) {
            endDate = '';
            dpkEnd.destroy();
        }

        var formatDatepicker = "dd/mm/yyyy";
        var prefixStartDate = '';
        var prefixEndDate = '';

        switch (chartType) {
            case '1':
                calendarWeeks = true;
                break;
            case '2':
                formatDatepicker = 'mm/yyyy';
                prefixStartDate = '01/';
                prefixEndDate = '01/';
                break;
            case '3':
                formatDatepicker = 'yyyy';
                prefixStartDate = '01/01';
                prefixEndDate = '01/01';
                break;
        }

        loadChart(reportType, chartType, null, null);

        dpkStart = $('#dpkStart').datepicker({
            uiLibrary: 'bootstrap4',
            format: formatDatepicker,
            calendarWeeks: calendarWeeks,
            change: function (e) {
                startDate = prefixStartDate + dpkStart.value();
                loadChart(reportType, chartType, startDate, endDate);
            }
        });

        dpkEnd = $('#dpkEnd').datepicker({
            uiLibrary: 'bootstrap4',
            format: formatDatepicker,
            calendarWeeks: calendarWeeks,
            change: function (e) {
                endDate = prefixEndDate + dpkEnd.value();
                loadChart(reportType, chartType, startDate, endDate);
            }
        });


        // loadChart(reportType, chartType, startDate, endDate);
    });
    var dpkStart = $('#dpkStart').datepicker({
        uiLibrary: 'bootstrap4',
        // format: 'dd/mm/yyyy',
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

    function drawChart(data, title, subtitle) {
        var options = {
            chart: {
                title: title,
                subtitle: subtitle
            },
            // width: 900,
            height: 500
        };
        mainDataTable = data;
        mainChart.draw(mainDataTable, google.charts.Line.convertOptions(options)); // Bar => Line (doi chart)
    }

    function createOrderChartData(xLable, chartData) {
        console.log(chartData);
        var data = new google.visualization.DataTable();
        data.addColumn('string', xLable);
        data.addColumn('number', 'New Orders');
        data.addColumn('number', 'Assigned Orders');
        data.addColumn('number', 'Bought Orders');
        data.addColumn('number', 'Received Orders');
        data.addColumn('number', 'Canceled Orders');
        data.addRows(chartData);
        return data;
    }

    function createRateChartData(xLable, chartData) {
        var data = new google.visualization.DataTable();
        data.addColumn('string', xLable);
        data.addColumn('number', 'Success rate');
        data.addColumn('number', 'Canceled rate');
        data.addRows(chartData);
        return data;
    }

    function createSoldProductChartData(xLable, chartData) {
        var data = new google.visualization.DataTable();
        data.addColumn('string', xLable);
        data.addColumn('number', 'Sold Products');
        data.addRows(chartData);
        return data;
    }

    function createTotalAmountChartData(xLable, chartData) {
        var data = new google.visualization.DataTable();
        data.addColumn('string', xLable);
        data.addColumn('number', 'Total Amount');
        data.addColumn('number', 'Paid Shipper Amount');
        data.addRows(chartData);
        return data;
    }

    function processChartData(data) {
        var labels = data.labels;
        var data = data.data;

        return data.map((row, idx) => {
            return [labels[idx]].concat(row);
        });
    }

    function loadChart(reportT, charT, start, end) {
        if (!start || !end) {
            // barChartExample.data.labels = [];
            // barChartExample.data.datasets = [];
            // barChartExample.update();
            // $("#chartName").text("");
            return;
        }

        var apiEndpoint = "orderchart";
        var chartTitle = "Orders";
        var startUnix = moment(start, "DD/MM/YYYY").unix() * 1000;
        var endUnix = moment(end, "DD/MM/YYYY").unix() * 1000;
        // console.log(reportT, charT, startUnix, endUnix);
        var charNameT = "";
        var xLabel = "";

        switch (reportT) {
            case "productSld":
                apiEndpoint = "soldproductchart";
                chartTitle = "Sold Products";
                charNameT += "The number of sold products by ";
                break;
            case "rateScc":
                apiEndpoint = "successratechart";
                chartTitle = "Success Delivery Rate";
                charNameT += "The rate of success by ";
                break;
            case "totalAmt":
                apiEndpoint = "incomeammountchart";
                chartTitle = "Total Amount";
                charNameT += "The total amount by ";
                break;
            case "shipPaid":
                apiEndpoint = "paidshipperchart";
                chartTitle = "Paid Shipper Amount";
                charNameT += "The paid amount for shippers by ";
                break;
            default:
                apiEndpoint = "orderchart";
                chartTitle = "Orders";
                charNameT += "The number of orders by ";
        }

        switch (charT) {
            case '0':
                charNameT += " days";
                xLabel = "Day";
                break;
            case '1':
                charNameT += " weeks";
                xLabel = "Week";
                break;
            case '2':
                charNameT += " months";
                xLabel = "Month";
                break;
            case '3':
                charNameT += " years";
                xLabel = "Year";
                break;
        }

        charNameT += " chart";
        // $("#chartName").text(charNameT);

        $.ajax({
            url: "/any/api/report/" + apiEndpoint + "?type=" + charT + "&start=" + startUnix + "&end=" + endUnix,
            type: "GET",
            dataType: "json",
            success: function (response) {
                // console.log(response.data);
                var chartData = response.data;
                var dataTable;
                chartData = processChartData(chartData);

                switch (reportT) {
                    case "productSld":
                        dataTable = createSoldProductChartData(xLabel, chartData);
                        break;
                    case "rateScc":
                        dataTable = createRateChartData(xLabel, chartData);
                        break;
                    case "totalAmt":
                        dataTable = createTotalAmountChartData(xLabel, chartData);
                        break;
                    default:
                        dataTable = createOrderChartData(xLabel, chartData);
                }

                drawChart(dataTable, charNameT, '');
            },
            error: function (err) {
                console.log(err);
            }
        });

    }

    function getDateOfWeek(w, y) {
        var d = ((w - 1) * 7 - 1); // 1st of January + 7 days for each week

        return new Date(y, 0, d);
    }

    function handleBarClick(selectedBar, status) {

        if (!selectedBar) {
            //     $("#valOrderCnt").html("--");
            //     $("#valCanceledOrderCnt").html("--");
            //     $("#valSuccessOrderCnt").html("--");
            //     $("#valRateOrder").html("--");
            //     $("#valSoldProductCnt").html("--");
            return;
        }

        // var status = 1;
        var startUnix1 = 0;
        var endUnix1 = 0;
        var labelOrderList = "List of ";

        switch (status) {
            case 1:
                labelOrderList += " new orders";
                break;
            case 2:
                labelOrderList += " assigned orders";
                break;
            case 3:
                labelOrderList += " bought orders";
                break;
            case 4:
                labelOrderList += " received orders";
                break;
            case 5:
                labelOrderList += " canceled orders";
                break;
            default:
                labelOrderList += " orders";
        }
        labelOrderList += " by ";

        switch (chartType) {
            case '0':
                // la ngay
                startMoment = moment(selectedBar, "DD/MM/YYYY");
                startUnix1 = startMoment.unix() * 1000;
                endUnix1 = startMoment.clone().add(1, 'days').unix() * 1000;
                labelOrderList += " days";
                break;

            case '1':
                //la tuan
                var parts = selectedBar.split("/");
                startMoment = moment(getDateOfWeek(parseInt(parts[0], 10), parseInt(parts[1], 10)));
                startUnix1 = startMoment.unix() * 1000;
                endUnix1 = startMoment.clone().add(1, 'w').unix() * 1000;
                labelOrderList += " weeks";
                break;

            case '2':
                startMoment = moment('01/' + selectedBar, "DD/MM/YYYY");
                startUnix1 = startMoment.unix() * 1000;
                endUnix1 = startMoment.clone().add(1, 'M').unix() * 1000;
                labelOrderList += " months";
                break;

            case '3':
                startUnix1 = moment("01/01/" + selectedBar, "DD/MM/YYYY").unix() * 1000;
                endUnix1 = moment("01/01/" + (parseInt(selectedBar) + 1), "DD/MM/YYYY").unix() * 1000;
                labelOrderList += " years";
                break;
        }

        $("#lbOrderList").text(labelOrderList);
        console.log(reportType, chartType, startUnix1, endUnix1);
        // console.log(day);
        $.ajax({
            url: "/any/api/report/orderlist?status=" + status + "&start=" + startUnix1 + "&end=" + endUnix1,
            type: "GET",
            dataType: "json",
            success: function (response) {
                var orders = response.data;
                orderList = orders;
                console.log(orders);

                var list = orders;
                // report-table
                $("#report-table").DataTable().destroy();
                var reportTable = $("#report-table").DataTable();
                reportTable.clear().draw();
                for (var i = 0; i < list.length; i++) {
                    var order = list[i];
                    reportTable.row.add([
                        '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + order.id + '</span>',
                        '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + order.buyerPhone + '</span>',
                        '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + order.buyerName + '</span>',
                        '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + order.shipperName + '</span>',
                        '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + order.totalPrice + '</span>',
                        fpsGetStatMsg(ORD_STAT_LIST, order.status)
                    ]).draw();
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    }

    // Xy ly pending
    $('#lbPenLess').click(function () {
        var now = moment();
        var low = now.clone().subtract(12, 'hours').unix() * 1000;
        var up = now.unix() * 1000;
        loadPendingOrders(low, up);
    });
    $('#lbPenEqual').click(function () {
        var now = moment();
        var low = now.clone().subtract(24, 'hours').unix() * 1000;
        var up = now.clone().subtract(12, 'hours').unix() * 1000;
        loadPendingOrders(low, up);
    });
    $('#lbPenMore').click(function () {
        var now = moment();
        var low = 0;
        var up = now.clone().subtract(24, 'hours').unix() * 1000;
        loadPendingOrders(low, up);
    });
    // low: chan duoi, up: chan tren
    // current local time: now
    // [now - 12, now)
    // [now - 24, now - 12]
    // [0, now - 24)
    function loadPendingOrders(low, up) {
        $("#lbOrderList").text("List of pending order");

        $.ajax({
            url: "/any/api/report/orderlist?status=1&start=" + low + "&end=" + up,
            type: "GET",
            dataType: "json",
            success: function (response) {
                var orders = response.data;
                orderList = orders;

                // report-table
                $("#report-table").DataTable().destroy();
                var reportTable = $("#report-table").DataTable();
                reportTable.clear().draw();
                for (var i = 0; i < orderList.length; i++) {
                    var order = orderList[i];
                    reportTable.row.add([
                        '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + order.id + '</span>',
                        '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + order.buyerPhone + '</span>',
                        '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + order.buyerName + '</span>',

                        '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + order.totalPrice + '</span>',
                        '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + order.status + '</span>',
                        fpsGetStatMsg(ORD_STAT_LIST, order.status)
                    ]).draw();
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    }
});

function fpsFormatDate(date) {
    var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    var day = date.getDate();
    var monthIndex = date.getMonth();
    var year = date.getFullYear();
    return day + ' ' + monthNames[monthIndex] + ' ' + year;
}

function getOrderDetail(pos) {
    orderEditPos = pos;
    console.log(orderList[pos], pos);
    if (typeof orderList[pos].createTime === "undefined") {
        $.ajax({
            type: 'GET',
            url: "/adm/api/order/detail",
            data: {orderId: orderList[pos].id},
            dataType: 'json',
            success: function (response) {
                orderList[pos] = response.data;
                orderEdit = orderList[pos];
                loadEditForm();
            },
            error: function (response) {
            }
        })
    } else {
        orderEdit = orderList[pos];
        loadEditForm();
    }
}

function loadEditForm() {

    uplBuyerFace.value = "";
    fpsSetImgSrc(imgBuyerFace, orderEdit.BuyerFace);

    uplBill.value = "";
    fpsSetImgSrc(imgBill, orderEdit.bill);

    txtOrderId.value = orderEdit.id;
    txtBuyerName.value = orderEdit.buyerName;
    txtBuyerPhone.value = orderEdit.buyerPhone;
    txtShipperName.value = orderEdit.shipperName;
    txtShipperPhone.value = orderEdit.shipperPhone;
    cbbStatus.value = orderEdit.status;

    txtLatitude.value = orderEdit.latitude;
    txtLongitude.value = orderEdit.longitude;

    txtTotalPrice.value = orderEdit.totalPrice;
    txtShipperEarn.value = orderEdit.shipperEarn;

    txtStoreName.value = orderEdit.storeName;
    txtCustomerDescription.value = orderEdit.customerDescription;

    $("#txtCreateTime").val(orderEdit.createTime ? moment.unix(orderEdit.createTime / 1000).format('DD/MM/YYYY') : '');
    $("#txtReceiveTime").val(orderEdit.receiveTime ? moment.unix(orderEdit.receiveTime / 1000).format('DD/MM/YYYY') : '');

    txtNote.value = orderEdit.note;


    tblBodyDetail.innerHTML = '';
    var list = orderEdit.detailList;
    for (var i = 0; i < list.length; i++) {
        var pro = list[i];
        var row = '  <td>' + (i + 1) + '</td>\n' +
            '            <td>' + pro.proName + '</td>\n' +
            '            <td>' + pro.unitPrice + '</td>\n' +
            '            <td>' + pro.quantity + '</td>\n' +
            '            <td>' + (pro.unitPrice * pro.quantity) + '</td>\n';
        tblBodyDetail.innerHTML += row;
    }

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
    if (newValue == oldValue) {
        return;
    }
    formData.append(paramName, newValue);
}
