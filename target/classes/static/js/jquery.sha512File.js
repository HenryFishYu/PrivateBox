/**
 * 
 */
jQuery.extend({
			sha512File:function (file) {
				var reader = new FileReader();
		        reader.readAsArrayBuffer(file);
		        reader.onprogress=function(event){
		        	console.log(event.loaded/event.total);
		        }
		        reader.onload = function(){
		        	console.log(CryptoJS.SHA512(CryptoJS.lib.WordArray.create(this.result)).toString());
		        	var sha512 = CryptoJS.algo.MD5.create(); 
		        	var piece;
		            for(piece=10000000;piece<this.result.byteLength;piece+=10000000){
		            	var pieceArrayBuffer=this.result.slice(piece-10000000,piece);
		            	var pieceWordArray=CryptoJS.lib.WordArray.create(pieceArrayBuffer);
		            	sha512.update(pieceWordArray);
		            }
		            var pieceArrayBuffer=this.result.slice(piece-10000000,this.result.byteLength);
	            	var pieceWordArray=CryptoJS.lib.WordArray.create(pieceArrayBuffer);
	            	sha512.update(pieceWordArray);
	            	
		            console.log(sha512.finalize().toString());
		        }
		        
			}
      });