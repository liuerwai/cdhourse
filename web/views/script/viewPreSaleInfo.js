
/**
 * 查询表格数据
 */
function  queryPreSaleInfo(){
    $.post("/queryPreSaleInfo",
        {
            startTime:$("#startTime").val(),
            endTime:$("#endTime").val(),
        },
        function(data){
            if(data.status){
                myTable.dynatable({
                    dataset: {
                        records: data.records
                    }
                });
                myTable.data('dynatable').settings.dataset.originalRecords = data.records;
                myTable.data('dynatable').process();
            } else {
                this.$message.error(data.msg);
            }
        }
    );
}


