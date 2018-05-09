
/**
 * 初始化表格
 */
function initEchart() {
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '成都主城区房屋供给，成交量'
        },
        tooltip: {},
        legend: {
            data:['面积']
        },
        xAxis: {
            data: ["新房预售","新房成交量","二手房成交量"]
        },
        yAxis: {},
        series: [{
            name: '销量',
            type: 'bar',
            data: []
        }]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

/**
 * 查询表格数据
 */
function  querySumInfo(){

    $.post("/querySum",
        {
            startTime:$("#startTime").val(),
            endTime:$("#endTime").val(),
        },
        function(data,status){
            if(data.status){
                myChart.setOption({
                    series: [{
                        data:[
                            data.records.preSaleMainCitySum,
                            data.records.dealNewSum,
                            data.records.dealSecondSum
                        ]
                    }]
                });
            } else {
                this.$message.error(data.msg);
            }
        }
    );
}


