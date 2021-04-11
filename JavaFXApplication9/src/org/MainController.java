package org;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author VINAY
 */
public class MainController implements Initializable {
    
    @FXML
    private TextField tfAccount;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfType;
    @FXML
    private TextField tfBranch;
    @FXML
    private TextField tfAmount;
    @FXML
    private TableView<Bank> tvBank;
    @FXML
    private TableColumn<Bank, Integer> colAccount;
    @FXML
    private TableColumn<Bank, String> colName;
    @FXML
    private TableColumn<Bank, String> colType;
    @FXML
    private TableColumn<Bank, String> colBranch;
    @FXML
    private TableColumn<Bank, Integer> colAmount;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnWithdraw;
    @FXML
    private Button btnDeposit;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        //label.setText("Hello World!");
        if(event.getSource() == btnNew)
        {
            insertAccount();
        }
        else if(event.getSource() == btnWithdraw)
        {
            Withdraw();
        }
        else if(event.getSource() == btnDeposit)
        {
            Deposit();
        }
        else if(event.getSource() == btnDelete)
        {
            DeleteAccount();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showBank();
    }

    public Connection getConnection()
    {
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\VINAY\\Desktop\\JAVA\\JavaFXApplication9\\src\\org\\Bank.db");
            return conn;
        }catch(Exception e)
        {
            System.out.println("Error:"+e.getMessage());
            return null;
        }
    }
    public ObservableList<Bank> getBankList()
    {
        ObservableList<Bank> bankList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "Select * from Bank";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Bank bank;
            while(rs.next())
            {
                bank = new Bank(rs.getInt("account"), rs.getString("name"), rs.getString("type"), rs.getString("branch"), rs.getInt("amount"));
                bankList.add(bank);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return bankList;
    }
    
    public void showBank()
    {
        ObservableList<Bank> list = getBankList();
        colAccount.setCellValueFactory(new PropertyValueFactory<Bank,Integer>("account"));
        colName.setCellValueFactory(new PropertyValueFactory<Bank,String>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<Bank,String>("type"));
        colBranch.setCellValueFactory(new PropertyValueFactory<Bank,String>("branch"));
        colAmount.setCellValueFactory(new PropertyValueFactory<Bank,Integer>("amount"));
        tvBank.setItems(list);
    }
    private void insertAccount()
    {
        String query = "INSERT into Bank VALUES (" + tfAccount.getText() + ",'" + tfName.getText() + "','" + tfType.getText() + "','" + tfBranch.getText() + "'," + tfAmount.getText() + ")";
        executeQuery(query);
        showBank();
    }
    
    private void Withdraw()
    {
        String query = "UPDATE Bank set amount = amount - " + tfAmount.getText() + " WHERE account = " + tfAccount.getText()+"";
        executeQuery(query);
        showBank();
    }
    
    private void Deposit()
    {
        String query = "UPDATE Bank set amount = amount + " + tfAmount.getText() + " WHERE account = " + tfAccount.getText()+"";
        executeQuery(query);
        showBank();
    }
    
    private void DeleteAccount()
    {
        String query = "DELETE from Bank where account = " + tfAccount.getText()+"";
        executeQuery(query);
        showBank();
    }
    
    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
