function loadMessages(){
  $.ajax({
    url  :'api/message',
    type :'GET',
    data :{
      '_csrf': $.cookie('XSRF-TOKEN')
    }
  })
  // リクエスト成功時
  .done((data) => {
    $('#messagesArea').html(data);
  })
  // リクエスト失敗時
  .fail( (data) => {
    alert("通信エラーが発生したため再読込して下さい。");
  })
}

function postMessage(){
  $.ajax({
    url  :'api/message',
    type :'POST',
    data :{
      '_csrf'   : $.cookie('XSRF-TOKEN'),
      'type'    : 'MESSAGE',
      'comment' : $('#message [name=comment]').val()
    }
  })
  // リクエスト成功時
  .done((data) => {
    $('#message [name=comment]').val(null);
    loadMessages();
  })
  // リクエスト失敗時
  .fail( (data) => {
    alert("通信エラーが発生したため再送信して下さい。");
  })
}

function postReply(targetFrom){
  $.ajax({
    url  :'api/message',
    type :'POST',
    data :{
      '_csrf'   : $.cookie('XSRF-TOKEN'),
      'type'    : 'REPLY',
      'postNo'  : $('#' + targetFrom + ' [name=postNo]').val(),
      'comment' : $('#' + targetFrom + ' [name=comment]').val()
    }
  })
  // リクエスト成功時
  .done((data) => {
    loadMessages();
  })
  // リクエスト失敗時
  .fail( (data) => {
    alert("通信エラーが発生したため再送信して下さい。");
  })
}

function deleteMessage(targetFrom){
  let messageId = $('#' + targetFrom + ' [name=id]').val()
  $.ajax({
    url  : 'api/message/' + messageId,
    type : 'DELETE',
    data :{
      '_csrf' : $.cookie('XSRF-TOKEN'),
    }
  })
  // リクエスト成功時
  .done((data) => {
    loadMessages();
  })
  // リクエスト失敗時
  .fail( (data) => {
    alert("通信エラーが発生したため再送信して下さい。");
  })
}

function postGood(messageId){

  $.ajax({
    url  : 'api/good/' + messageId,
    type : 'POST',
    data :{
      '_csrf' : $.cookie('XSRF-TOKEN')
    }
  })
  // リクエスト成功時
  .done((data) => {
    loadMessages();
  })
  // リクエスト失敗時
  .fail( (data) => {
    alert("通信エラーが発生したため再送信して下さい。");
  })
}

function deleteGood(messageId){

  $.ajax({
    url  : 'api/good/' + messageId,
    type : 'DELETE',
    data :{
      '_csrf' : $.cookie('XSRF-TOKEN')
    }
  })
  // リクエスト成功時
  .done((data) => {
    loadMessages();
  })
  // リクエスト失敗時
  .fail( (data) => {
    alert("通信エラーが発生したため再送信して下さい。");
  })
}