package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private int comp;
    private int number;
    private int impt;
    private int multi;

    public TodoItem(String title, String desc, String category, String due_date){
        this.title=title;
        this.desc=desc;
        this.category=category;
        this.due_date=due_date;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date = f.format(new Date());
        this.comp = 0;
    }
    
	public TodoItem(String title, String desc, String category, String due_date, int comp){
        this.title=title;
        this.desc=desc;
        this.category=category;
        this.due_date=due_date;
        this.comp=comp;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date = f.format(new Date());
    }
	
	public TodoItem(String title, String desc, String category, String due_date, int comp, int impt){
        this.title=title;
        this.desc=desc;
        this.category=category;
        this.due_date=due_date;
        this.comp=comp;
        this.impt=impt;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date = f.format(new Date());
    }
	
	public TodoItem(String title, String desc, String category, String due_date, int comp, int impt, int multi){
        this.title=title;
        this.desc=desc;
        this.category=category;
        this.due_date=due_date;
        this.comp=comp;
        this.impt=impt;
        this.multi=multi;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date = f.format(new Date());
    }
    
    public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	
	public int getIsComp() {
		return comp;
	}

	public void setIsComp(int comp) {
		this.comp = comp;
	}

	public String toSaveString() {
    	return category + "##" + title + "##" + desc + "##" + due_date + "##" + current_date + "\n";
    }
    
    @Override
    public String toString() {
    	if (this.comp == 1 && this.impt == 1 && this.multi == 1) return number + ". " + "[V]*[" + category + "]" + " " + title + "(팀플) - " + desc + " - " + due_date + " - " + current_date;
    	else if (this.comp == 1 && this.impt == 0 && this.multi == 0) return number + ". " + "[V][" + category + "]" + " " + title + " - " + desc + " - " + due_date + " - " + current_date;
    	else if (this.comp == 0 && this.impt == 1 && this.multi == 0) return number + ". " + "*[" + category + "]" + " " + title + " - " + desc + " - " + due_date + " - " + current_date;
    	else if (this.comp == 0 && this.impt == 0 && this.multi == 1) return number + ". " + "[" + category + "]" + " " + title + "(팀플) - " + desc + " - " + due_date + " - " + current_date;
    	else if (this.comp == 0 && this.impt == 1 && this.multi == 1) return number + ". " + "*[" + category + "]" + " " + title + "(팀플) - " + desc + " - " + due_date + " - " + current_date;
    	else if (this.comp == 1 && this.impt == 1 && this.multi == 0) return number + ". " + "[V]*[" + category + "]" + " " + title + " - " + desc + " - " + due_date + " - " + current_date;
    	else if (this.comp == 1 && this.impt == 0 && this.multi == 1) return number + ". " + "[V][" + category + "]" + " " + title + "(팀플) - " + desc + " - " + due_date + " - " + current_date;
    	else return number + ". " + "[" + category + "]" + " " + title + " - " + desc + " - " + due_date + " - " + current_date;
    }
    
    public String findString() {
    	return category + title + desc + due_date + current_date;
    }
    
    public int getIsImpt() {
    	return impt;
    }
	public void setIsImpt(int impt) {
		this.impt = impt;
	}
	
	public int getIsMulti() {
    	return multi;
    }
	public void setIsMulti(int multi) {
		this.multi = multi;
	}
}
