package tddmonkey.rxdynamo.awssdk;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemResult;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.amazonaws.services.dynamodbv2.model.UpdateTableRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTableResult;
import java.lang.Integer;
import java.lang.String;
import java.util.Map;
import rx.Observable;

public class AmazonSdkRxDynamoDb {
  private final AmazonDynamoDBAsync amazonClient;

  public AmazonSdkRxDynamoDb(AmazonDynamoDBAsync amazonClient) {
    this.amazonClient = amazonClient;
  }

  public Observable<BatchGetItemResult> batchGetItem(Map map) {
    return Observable.create(subscriber -> amazonClient.batchGetItemAsync(map, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<BatchGetItemResult> batchGetItem(BatchGetItemRequest batchGetItemRequest) {
    return Observable.create(subscriber -> amazonClient.batchGetItemAsync(batchGetItemRequest, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<BatchWriteItemResult> batchWriteItem(Map map) {
    return Observable.create(subscriber -> amazonClient.batchWriteItemAsync(map, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<BatchWriteItemResult> batchWriteItem(BatchWriteItemRequest batchWriteItemRequest) {
    return Observable.create(subscriber -> amazonClient.batchWriteItemAsync(batchWriteItemRequest, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<CreateTableResult> createTable(CreateTableRequest createTableRequest) {
    return Observable.create(subscriber -> amazonClient.createTableAsync(createTableRequest, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<DeleteItemResult> deleteItem(DeleteItemRequest deleteItemRequest) {
    return Observable.create(subscriber -> amazonClient.deleteItemAsync(deleteItemRequest, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<DeleteTableResult> deleteTable(String string) {
    return Observable.create(subscriber -> amazonClient.deleteTableAsync(string, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<DeleteTableResult> deleteTable(DeleteTableRequest deleteTableRequest) {
    return Observable.create(subscriber -> amazonClient.deleteTableAsync(deleteTableRequest, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<DescribeTableResult> describeTable(String string) {
    return Observable.create(subscriber -> amazonClient.describeTableAsync(string, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<DescribeTableResult> describeTable(DescribeTableRequest describeTableRequest) {
    return Observable.create(subscriber -> amazonClient.describeTableAsync(describeTableRequest, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<GetItemResult> getItem(GetItemRequest getItemRequest) {
    return Observable.create(subscriber -> amazonClient.getItemAsync(getItemRequest, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<ListTablesResult> listTables(String string) {
    return Observable.create(subscriber -> amazonClient.listTablesAsync(string, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<ListTablesResult> listTables(Integer integer) {
    return Observable.create(subscriber -> amazonClient.listTablesAsync(integer, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<ListTablesResult> listTables(ListTablesRequest listTablesRequest) {
    return Observable.create(subscriber -> amazonClient.listTablesAsync(listTablesRequest, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<PutItemResult> putItem(PutItemRequest putItemRequest) {
    return Observable.create(subscriber -> amazonClient.putItemAsync(putItemRequest, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<QueryResult> query(QueryRequest queryRequest) {
    return Observable.create(subscriber -> amazonClient.queryAsync(queryRequest, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<ScanResult> scan(ScanRequest scanRequest) {
    return Observable.create(subscriber -> amazonClient.scanAsync(scanRequest, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<UpdateItemResult> updateItem(UpdateItemRequest updateItemRequest) {
    return Observable.create(subscriber -> amazonClient.updateItemAsync(updateItemRequest, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }

  public Observable<UpdateTableResult> updateTable(UpdateTableRequest updateTableRequest) {
    return Observable.create(subscriber -> amazonClient.updateTableAsync(updateTableRequest, AmazonWebServiceRequestAsyncHandler.valueEmittingHandlerFor(subscriber)));
  }
}