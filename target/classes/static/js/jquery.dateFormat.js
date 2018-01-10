/**
 * 
 */
jQuery.extend({
              // 设置 apDiv
			timeStamp2String:function (time) {
            //apDiv浮动层显示位置居中控制
				 var datetime = new Date();
		         datetime.setTime(time);
		         var year = datetime.getFullYear();
		         var month = datetime.getMonth() + 1;
		         if(month<10){
		        	 month="0"+month;
		         }
		         var date = datetime.getDate();
		         if(date<10){
		        	 date="0"+date;
		         }
		         var hour = datetime.getHours();
		         if(hour<10){
		        	 hour="0"+hour;
		         }
		         var minute = datetime.getMinutes();
		         if(minute<10){
		        	 minute="0"+minute;
		         }
		         var second = datetime.getSeconds();
		         if(second<10){
		        	 second="0"+second;
		         }
		         return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
		         /*var mseconds = datetime.getMilliseconds();*/
		         /*return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second+"."+mseconds;*/
            }
      });