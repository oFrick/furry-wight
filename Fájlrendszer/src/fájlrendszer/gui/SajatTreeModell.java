package fájlrendszer.gui;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import fájlrendszer.main.Fájl;
import fájlrendszer.main.Könyvtár;

public class SajatTreeModell extends DefaultTreeModel {

	public SajatTreeModell(TreeNode name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public SajatTreeModell(TreeNode root, boolean asksAllowsChildren) {
		super(root, asksAllowsChildren);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultTreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
	 */
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub
		//super.valueForPathChanged(path, newValue);
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();

	    if(node.getUserObject() instanceof Fájl) ((Fájl)node.getUserObject()).setNév((String)newValue);
	    else if (node.getUserObject() instanceof Könyvtár) ((Könyvtár)node.getUserObject()).setNév((String)newValue);
	    
	    nodeChanged(node);
	}
	
	

}
