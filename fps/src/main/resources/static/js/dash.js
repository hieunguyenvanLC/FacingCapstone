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
    txtStoreAddress = document.getElementById("txtStoreAddress");
    txtStoreName = document.getElementById("txtStoreName");
    txtCustomerDescription = document.getElementById("txtCustomerDescription");
    txtNote = document.getElementById("txtNote");

    btnCloseModal = document.getElementById("btnCloseModal");
    tblBodyDetail = document.getElementById("tblBodyDetail");

    function loadSummary() { //Load Summary
        $.ajax({
            // url: "/any/api/report/summary?mon=7&year=2019",
            url: "/any/api/report/summary",
            type: "GET",
            dataType: "json",
            success: function (response) {
                // console.log(response.data);
                var summary = response.data;
                //shipper
                $("#lbActivedShipper").html("<strong>" + abbrNum(summary.shipperCount) + "</strong>"); //Shipper all
                $("#lbActivedShipperTDay").html("<strong>" + abbrNum(summary.shipperCountTDay) + "</strong>"); //Shipper day
                $("#lbActivedShipperTWeek").html("<strong>" + abbrNum(summary.shipperCountTWeek) + "</strong>");//Shipper week
                $("#lbActivedShipperTMonth").html("<strong>" + abbrNum(summary.shipperCountTMonth) + "</strong>");//Shipper month
                //customer
                $("#lbNewCus").html("<strong>" + abbrNum(summary.CustomerCount) + "</strong>"); //customer all
                $("#lbNewCusTDay").html("<strong>" + abbrNum(summary.CustomerCountTDay) + "</strong>");//customer day
                $("#lbNewCusTWeek").html("<strong>" + abbrNum(summary.CustomerCountTWeek) + "</strong>");//customer week
                $("#lbNewCusTMonth").html("<strong>" + abbrNum(summary.CustomerCountTMonth) + "</strong>");//customer month
                //Store
                $("#lbNewStores").html("<strong>" + abbrNum(summary.StoreCount) + "</strong>");   //Store all
                $("#lbNewStoresTDay").html("<strong>" + abbrNum(summary.StoreCountTDay) + "</strong>");//Store day
                $("#lbNewStoresTWeek").html("<strong>" + abbrNum(summary.StoreCountTWeek) + "</strong>");//Store week
                $("#lbNewStoresTMonth").html("<strong>" + abbrNum(summary.StoreCountTMonth) + "</strong>");//Store month
                //Order
                $("#lbNewOrder").html("<strong>" + abbrNum(summary.OrderCount) + "</strong>");  // all
                $("#lbNewOrderTDay").html("<strong>" + abbrNum(summary.OrderCountTDay) + "</strong>"); //day
                $("#lbNewOrderTWeek").html("<strong>" + abbrNum(summary.OrderCountTWeek) + "</strong>"); // week
                $("#lbNewOrderTMonth").html("<strong>" + abbrNum(summary.OrderCountTMonth) + "</strong>"); // month
                //Rate
                $("#lbSuccessRate").html("<strong>" + summary.successRate + "%</strong>");// all
                $("#lbSuccessRateD").html("<strong>" + summary.successRateTDay + "%</strong>");//day
                $("#lbSuccessRateW").html("<strong>" + summary.successRateTWeek + "%</strong>");// week
                $("#lbSuccessRateM").html("<strong>" + summary.successRateTMonth + "%</strong>");// month
                //Total
                $("#lbTotalAmount").html("<strong>" + abbrNum(summary.totalAmount) + " VND</strong>");// all
                $("#lbTotalAmountD").html("<strong>" + abbrNum(summary.totalAmountTDay) + " VND</strong>");//day
                $("#lbTotalAmountW").html("<strong>" + abbrNum(summary.totalAmountTWeek) + " VND</strong>");// week
                $("#lbTotalAmountM").html("<strong>" + abbrNum(summary.totalAmountTMonth) + " VND</strong>"); // month
                //Paid
                $("#lbPaidShipper").html("<strong>" + abbrNum(summary.paidShipper) + " VND</strong>");// all
                $("#lbPaidShipperD").html("<strong>" + abbrNum(summary.paidShipperTDay) + " VND</strong>");//day
                $("#lbPaidShipperW").html("<strong>" + abbrNum(summary.paidShipperTWeek) + " VND</strong>");// week
                $("#lbPaidShipperM").html("<strong>" + abbrNum(summary.paidShipperTMonth) + " VND</strong>");// month
                //Sold Product
                $("#lbSoldProduct").html("<strong>" + abbrNum(summary.soldProductCount) + "</strong>");// all
                $("#lbSoldProductD").html("<strong>" + abbrNum(summary.soldProductCountTDay) + "</strong>");//day
                $("#lbSoldProductW").html("<strong>" + abbrNum(summary.soldProductCountTWeek) + "</strong>");// week
                $("#lbSoldProductM").html("<strong>" + abbrNum(summary.soldProductCountTMonth) + "</strong>");// month

            },
            error: function (err) {
                console.log(err);
            }
        });
    }

    loadSummary();
    setInterval(loadSummary, 10000); //Reset time

    // Init chart
    var mainDataTable = null;
    var mainChart = null;
    google.charts.load('current', {'packages': ['line']}); // bar => line (doi chart)
    google.charts.setOnLoadCallback(function () {
        mainChart = new google.charts.Line(document.getElementById('barChartExample')); // Bar => Line (doi chart)

        google.visualization.events.addListener(mainChart, 'select', selectHandler);

        function selectHandler(e) { //Select click chart
            // alert('A table row was selected');
            var selections = mainChart.getSelection();
            // console.log(selections);
            if (selections.length) {
                var selection = selections[0];
                if (selection.column) {
                    // console.log(e, mainDataTable.getValue(selection.row, 0));
                    var status = selection.column;
                    var startMoment = null;
                    var endMoment = null;
                    var startUnix1 = 0;
                    var endUnix1 = 0;

                    // Lay start date va end date tu selection.row
                    if (selection.row) {
                        var selectedLabel = mainDataTable.getValue(selection.row, 0);
                        switch (chartType) {
                            case '0':
                                // la ngay
                                startMoment = moment(selectedLabel, "DD/MM/YYYY");
                                startUnix1 = startMoment.unix() * 1000;
                                endUnix1 = startMoment.clone().add(1, 'days').unix() * 1000;
                                break;
                            // IPhone iPhone5 = iPhone4.clone();
                            // iPhone5.setThietKe(new ThietKe12254512());
                            // iPhone5.setHeDieuHanh(new HeDieuHanh12355());
                            // iPhone5.setDungLuongPin(n));
                            case '1':
                                //la tuan
                                var parts = selectedLabel.split("/");
                                startMoment = moment(getDateOfWeek(parseInt(parts[0], 10), parseInt(parts[1], 10)));
                                startUnix1 = startMoment.unix() * 1000;
                                endUnix1 = startMoment.clone().add(1, 'w').unix() * 1000;
                                break;

                            case '2'://thang
                                startMoment = moment('01/' + selectedLabel, "DD/MM/YYYY");
                                startUnix1 = startMoment.unix() * 1000;
                                endUnix1 = startMoment.clone().add(1, 'M').unix() * 1000;
                                break;

                            case '3': //nam
                                startUnix1 = moment("01/01/" + selectedLabel, "DD/MM/YYYY").unix() * 1000;
                                endUnix1 = moment("01/01/" + (parseInt(selectedLabel) + 1), "DD/MM/YYYY").unix() * 1000;
                                break;
                        }
                    } else {
                        startMoment = moment(startDate, "DD/MM/YYYY");
                        endMoment = moment(endDate, "DD/MM/YYYY");
                        switch (chartType) {
                            case '0':
                                endMoment.hours(23).minute(59).second(59).millisecond(999);
                                break;
                            case '1':
                                endMoment.hours(23).minute(59).second(59).millisecond(999);
                                break;
                            case '2':
                                endMoment.add(1, 'M');
                                break;
                            case '3':
                                endMoment.add(1, 'Y');
                                break;
                        }
                        startUnix1 = startMoment.unix() * 1000;
                        endUnix1 = endMoment.unix() * 1000;
                    }

                    switch (reportType) { // Chuyen status theo legend doi voi chart khac
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
                    handleBarClick(startUnix1, endUnix1, status);
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
                calendarWeeks = true; //doi font
                break;
            case '2':
                formatDatepicker = 'mm/yyyy';
                prefixStartDate = '01/';
                prefixEndDate = '01/';
                break;
            case '3':
                formatDatepicker = 'yyyy';
                prefixStartDate = '01/01/';
                prefixEndDate = '01/01/';
                break;
        }

        clearChart(); // reset chart

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

    function clearChart() {
        if (mainChart) {
            // Clear chart
            // mainChart.clearChart();
            // Clear data table
            loadReportTable(null, null, '', '');
        }
    }

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
        // console.log(chartData);
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

    function processChartData(data) { // { labels, data[] } => [label, data1, data2, data 3, ...]
        var labels = data.labels;
        var data = data.data;

        return data.map(function (row, idx) {
            return [labels[idx]].concat(row);
        });
    }

    function loadChart(reportT, charT, start, end) {
        if (!start || !end) {
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
                loadReportTable(null, null, '', '');
            },
            error: function (err) {
                console.log(err);
            }
        });

    }

    function getDateOfWeek(w, y) {
        var dowOfFirstDay = (new Date(y, 0, 1)).getDay(); // 0 (sun) -> 6 (sat)
        var d = ((w - 1) * 7 - dowOfFirstDay + 1); // 1st of January + 7 days for each week

        return new Date(y, 0, d);
    }

    function handleBarClick(startUnixT, endUnixT, status) {

        if (!startUnixT || !endUnixT) {
            //     $("#valOrderCnt").html("--");
            //     $("#valCanceledOrderCnt").html("--");
            //     $("#valSuccessOrderCnt").html("--");
            //     $("#valRateOrder").html("--");
            //     $("#valSoldProductCnt").html("--");
            return;
        }

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
            case '0': // la ngay
                labelOrderList += " days";
                break;

            case '1': //la tuan
                labelOrderList += " weeks";
                break;

            case '2':
                labelOrderList += " months";
                break;

            case '3':
                labelOrderList += " years";
                break;
        }

        loadReportTable(startUnixT, endUnixT, status, labelOrderList);
    }

    var tblMainReport = null;
    var drdTableCol = null;
    var drdTableColValue = null;
    var columnMap = {};
    var columnIndices = ['buyerPhone', 'buyerName', 'shipperPhone', 'shipperName', 'storeName', 'totalPrice', 'createTime', 'status'];
    var filterCol = '';
    var filterColVal = '';

    function loadReportTable(startUnixT, endUnixT, status, title) {
        if (!startUnixT || !endUnixT) {
            if (tblMainReport) {
                tblMainReport.clear().draw();
            }
            return;
        }

        columnMap = {};
        filterCol = '';
        filterColVal = '';

        $("#lbOrderList").text(title);
        $.ajax({
            url: "/any/api/report/orderlist?status=" + status + "&start=" + startUnixT + "&end=" + endUnixT,
            // url: "/any/api/report/orderlist?status=" + status + "&start=" + dpkStart + "&end=" + dpkEnd,
            type: "GET",
            dataType: "json",
            success: function (response) {
                var orders = response.data;
                columnMap = createColMap(orders);
                // console.log(columnMap);
                orderList = orders;
                var list = orders;
                // report-table
                $("#report-table").DataTable().destroy();
                tblMainReport = $("#report-table").DataTable({
                    "dom": "<'row'<'col-sm-12 col-md-3'l><'col-sm-12 col-md-5 char-filter daterange'><'col-sm-12 col-md-4'f>>" +
                        "<'row'<'col-sm-12'tr>>" +
                        "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
                    fnInitComplete: function () {
                        $('div.daterange').html('<label class="item-flex-auto">Type:</label><select class="item-flex-auto" id="drdTableCol" width="130px"></select>' +
                            '<label class="item-flex-auto">Value:</label><select class="item-flex-fill" id="drdTableColValue" width="200px"></select>');
                        if (drdTableCol) {
                            drdTableCol.destroy();
                            drdTableCol = null;
                        }
                        if (drdTableColValue) {
                            drdTableColValue.destroy();
                            drdTableColValue = null;
                        }
                        var drdTableCol = $('#drdTableCol').dropdown({
                            textField: 'name',
                            dataSource: [
                                {value: 'buyerPhone', name: 'Buyer Phone'},
                                {value: 'buyerName', name: 'Buyer Name'},
                                {value: 'shipperPhone', name: 'Shipper Phone'},
                                {value: 'shipperName', name: 'Shipper Name'},
                                {value: 'storeName', name: 'Store'},
                                {value: 'createTime', name: 'Create Time'}
                            ],
                            change: function (e) {
                                // alert('Change is fired');
                                // console.log('test', drdTableCol.value());
                                filterCol = drdTableCol.value();
                                if (drdTableColValue) {
                                    drdTableColValue.destroy();
                                    drdTableColValue = null;
                                }

                                drdTableColValue = $('#drdTableColValue').dropdown({
                                    textField: 'name',
                                    dataSource: columnMap[filterCol],
                                    change: function (e) {
                                        filterColVal = drdTableColValue.value();
                                        console.log(filterCol, filterColVal);
                                        tblMainReport.draw();
                                    }
                                });
                            }
                        });
                        var drdTableColValue = $('#drdTableColValue').dropdown({
                            textField: 'name',
                            dataSource: []
                        });
                    }
                });
                tblMainReport.clear().draw();
                for (var i = 0; i < list.length; i++) {
                    var order = list[i];
                    tblMainReport.row.add([
                        // '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + order.id + '</span>',
                        '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + order.buyerPhone + '</span>',
                        '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + order.buyerName + '</span>',
                        '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + order.shipperPhone + '</span>',
                        '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + order.shipperName + '</span>',
                        '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + order.storeName + '</span>',
                        '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + (order.totalPrice) + '</span>',
                        '<span data-target="#statistic-detail-modal" data-toggle="modal" onclick="getOrderDetail(' + i + ')">' + (order.createTime ? moment.unix(order.createTime / 1000).format('DD/MM/YYYY') : '') + '</span>',
                        fpsGetStatMsg(ORD_STAT_LIST, order.status)
                    ]).draw();
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    }

    function createColMap(orders) {
        var map = {};

        for (var i = 0; i < orders.length; i++) {
            var order = orders[i];
            var keys = Object.keys(order);
            for (var j = 0; j < keys.length; j++) {
                var key = keys[j];

                if (!map[key]) {
                    map[key] = [];
                }

                var val = order[key];
                if (key === 'createTime') {
                    val = (val ? moment.unix(val / 1000).format('DD/MM/YYYY') : '')
                }

                if (!map[key].find(function (item) {
                    return item.value === val
                })) {
                    map[key].push({
                        value: val,
                        name: val
                    });
                }
            }
        }

        return map;
    }

    $.fn.dataTable.ext.search.push(
        function (settings, data, dataIndex) {
            if (settings.sTableId === "report-table") {
                console.log(data)
                if (filterCol && filterColVal) {
                    if ((data[columnIndices.indexOf(filterCol)] === filterColVal)) {
                        return true;
                    }

                    return false;
                }
            }

            return true;
        }
    );
});

function abbrNum(number) {
    decPlaces = 3;
    // 2 decimal places => 100, 3 => 1000, etc
    decPlaces = Math.pow(10, decPlaces);

    // Enumerate number abbreviations
    var abbrev = ["k", "m", "b", "t"];

    // Go through the array backwards, so we do the largest first
    for (var i = abbrev.length - 1; i >= 0; i--) {

        // Convert array index to "1000", "1000000", etc
        var size = Math.pow(10, (i + 1) * 3);

        // If the number is bigger or equal do the abbreviation
        if (size <= number) {
            // Here, we multiply by decPlaces, round, and then divide by decPlaces.
            // This gives us nice rounding to a particular decimal place.
            number = Math.round(number * decPlaces / size) / decPlaces;

            // Handle special case where we round up to the next abbreviation
            if ((number == 1000) && (i < abbrev.length - 1)) {
                number = 1;
                i++;
            }

            // Add the letter for the abbreviation
            number += abbrev[i];

            // We are done... stop
            break;
        }
    }

    return number;
}

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
    cbbStatus.value = changeStatus(orderEdit.status);

    txtLatitude.value = orderEdit.latitude;
    txtLongitude.value = orderEdit.longitude;

    txtTotalPrice.value = orderEdit.totalPrice;
    txtShipperEarn.value = orderEdit.shipperEarn;

    txtStoreName.value = orderEdit.storeName;
    txtStoreAddress.value = orderEdit.storeAddress;
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

function changeStatus(status) {
    if (status == 1) {
        status = "New";
    }
    if (status == 2) {
        status = "Assigned";
    }
    if (status == 3) {
        status = "Bought";
    }
    if (status == 4) {
        status = "Done";
    }
    if (status == 5) {
        status = "Canceled";
    }
    return status;
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
