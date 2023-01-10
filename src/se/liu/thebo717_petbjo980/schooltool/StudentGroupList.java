package se.liu.thebo717_petbjo980.schooltool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class which represents a list of StudentGroup-objects. Is used
 * to handle all StudentGroups registered to a School object.
 */
public class StudentGroupList
{
    private List<StudentGroup> studentGroups;

    public StudentGroupList(){
	this.studentGroups = new ArrayList<>();
    }
    public void add(StudentGroup group){
	studentGroups.add(group);
    }

    public StudentGroup getStudentGroup(String name){
	for(StudentGroup group : studentGroups){
	    //nedan kollar vi INTE typen, som det står i kodanalysen, utan vi jämför helt enkelt namnen
	    if(Objects.equals(group.getName(), name)){
		return group;
	    }
	}
	return null;
    }

    public boolean isEmpty(){
	return studentGroups.isEmpty();
    }

    public boolean contains(StudentGroup group){
	return studentGroups.contains(group);
    }

    public void remove(StudentGroup group){
	group.unregisterAll();
	studentGroups.remove(group);
    }

    public List<StudentGroup> getStudentGroups(){
	return studentGroups;
    }
}
