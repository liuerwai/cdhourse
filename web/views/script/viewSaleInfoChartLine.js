/**
 * 初始化表格
 */
function initEchart() {
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '折线图堆叠'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['新房', '二手房', '新房郊区', '二手房郊区', '新房城区', '二手房城区']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: []
        },
        yAxis: {
            type: 'value'
        },
        series: []
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

/**
 * 查询表格数据
 */
function querySumInfo() {

    $.post("/cdhouse/queryDealInfoForChartLine",
        {
            startTime: $("#startTime").val(),
            endTime: $("#endTime").val(),
        },
        function (data, status) {
            if (data.status) {
                myChart.setOption({
                    series: [
                        {
                            name: '新房',
                            type: 'line',
                            data: data.records.newHourse
                        },
                        {
                            name: '二手房',
                            type: 'line',
                            data: data.records.secondHourse
                        },
                        // {
                        //     name: '新房郊区',
                        //     type: 'line',
                        //     data: data.records.newHourseDistantCity
                        // },
                        // {
                        //     name: '二手房郊区',
                        //     type: 'line',
                        //     data: data.records.secondHourseDistantCity
                        // },
                        {
                            name: '新房城区',
                            type: 'line',
                            data: data.records.newHourseMainCity
                        },
                        {
                            name: '二手房城区',
                            type: 'line',
                            data: data.records.secondHourseMainCity
                        },
                    ],
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: data.records.X
                    }
                });
            } else {
                this.$message.error(data.msg);
            }
        }
    );
}


