package hello;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BinaryDataController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    public static BinarySearchTree<Student> Tree;

    @CrossOrigin(origins="http://localhost:3000")
    @RequestMapping("/getree")
    public BinaryNodeData GetTree() {
        CheckTree();
        return TreeData();
    }

    @CrossOrigin(origins="http://localhost:3000")
    @RequestMapping("/minimum")
    public Student GeMinimum() {
        CheckTree();
        return BinaryDataController.Tree.GetMinimum().GetInfo();
    }

    @CrossOrigin(origins="http://localhost:3000")
    @RequestMapping("/maximum")
    public Student GeMaximum() {
        CheckTree();
        return BinaryDataController.Tree.GetMaximum().GetInfo();
    }

    @CrossOrigin(origins="http://localhost:3000")
    @RequestMapping("/median")
    public Student GetMedian() {
        CheckTree();
        return BinaryDataController.Tree.GetMedian().GetInfo();
    }

    @CrossOrigin(origins="http://localhost:3000")
    @RequestMapping("/size")
    public int GetSize() {
        CheckTree();
        return BinaryDataController.Tree.GetSize();
    }

    @CrossOrigin(origins="http://localhost:3000")
    @RequestMapping("/inorder")
    public String[] InorderStart() {
        CheckTree();
        return BinaryDataController.Tree.InorderStart();
    }


    @CrossOrigin(origins="http://localhost:3000")
    @RequestMapping("/preorder")
    public String[] PreorderStart() {
        CheckTree();
        return BinaryDataController.Tree.PreorderStart();
    }


    @CrossOrigin(origins="http://localhost:3000")
    @RequestMapping("/postorder")
    public String[] PostorderStart() {
        CheckTree();
        return BinaryDataController.Tree.PostorderStart();
    }
    @CrossOrigin(origins="http://localhost:3000")
    @RequestMapping("/insertnode/{id}/{name}")
    public BinaryNodeData CreateTree(@PathVariable int id,@PathVariable String name) {
        CheckTree();
        BinaryDataController.Tree.Insert(
            new BinaryNode<Student>(
                new Student(name,id)
            ));
        return TreeData();
    }

    @CrossOrigin(origins="http://localhost:3000")
    @RequestMapping("/search/{id}")
    public Student SearchTree(@PathVariable int id) {
        CheckTree();
        BinaryNode<Student> node=BinaryDataController.Tree.Search(new Student("name", id));
        if(node==null){
            return new Student("No results", -1);
        }
        return node.GetInfo();
    }

    @CrossOrigin(origins="http://localhost:3000")
    @RequestMapping("/delete/{id}")
    public DeleteData Delete(@PathVariable int id) {
        CheckTree();
        BinaryNode<Student> node=BinaryDataController.Tree.Delete(new Student("name", id));
        if(node==null){
            return new DeleteData(TreeData(),new Student("No results", -1));
        }
        return new DeleteData(TreeData(),node.GetInfo());
    }

    public BinaryNodeData TreeData(){
        return ConvertTree(BinaryDataController.Tree.GetRoot());
    }

    public void CheckTree(){
        if(BinaryDataController.Tree==null){
            BinaryDataController.Tree=new BinarySearchTree<Student>();
        }
    }

    public BinaryNodeData ConvertTree(BinaryNode<Student> node){
        BinaryNodeData dataNode=new BinaryNodeData();
        if(node==null){
            dataNode=new BinaryNodeData("no data",new Student("no data", 0),null);
            return dataNode;
        }
        dataNode.Name=String.valueOf(node.GetKey());
        dataNode.Attributes=node.GetInfo();
        
        List<BinaryNodeData> list=new ArrayList<BinaryNodeData>();
        if(node.IsParentOfLeft()){
            list.add(ConvertTree(node.GetLeft()));
        }else{
            ApplyLeft(list,node);
        }

        if(node.IsParentOfRight()){
            list.add(ConvertTree(node.GetRight()));
        }else{
            ApplyRight(list, node);
        }

        dataNode.Children=new BinaryNodeData[list.size()];
        dataNode.Children=list.toArray(dataNode.Children);
        return dataNode;
    }  

    public void ApplyLeft(List<BinaryNodeData> list,BinaryNode<Student> node){
        if(node.GetLeft()==null){
            list.add(new BinaryNodeData("NUll",new Student("NUll",0),null));
            return;
        }
        list.add(new BinaryNodeData("Predecessor",node.GetLeft().GetInfo(),null));
    }


    public void ApplyRight(List<BinaryNodeData> list,BinaryNode<Student> node){
        if(node.GetRight()==null){
            list.add(new BinaryNodeData("NUll",new Student("NUll",0),null));
            return;
        }
        list.add(new BinaryNodeData("Successor",node.GetRight().GetInfo(),null));
    }
}