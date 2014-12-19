/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving.dao;

import android.content.Context;
import android.database.Cursor;

import com.gedevanishvili.driving.db.DatabaseManager;
import com.gedevanishvili.driving.db.SQLiteDBManager;
import com.gedevanishvili.driving.modules.ExamQuestion;
import com.gedevanishvili.driving.modules.MyAlert;

/**
 * 
 * @author alexx Data Access Object class of test questions
 */
public class ExamTicketsDao {
	/*
	 * type = 1 exam; type = 2 theme tickets; theme tickets would be added in
	 * the next edition
	 */
	private int type;
	private int cnt;
	private int catId;
	private String catName;
	private int nextCatId;
	private String nextCatName;
	DatabaseManager dbManager;

	/**
	 * Initialize class properties
	 */
	public ExamTicketsDao(Context context, int t, int catId) {
		this.type = t;
		this.catId = catId;

		// open sqlite database
		dbManager = new SQLiteDBManager(context);
		dbManager.openDatabase();

		// init category parameters
		if (this.type == 2) {
			this.initCatParams();
		}
	}

	/**
	 * Returns array of questions
	 */
	public ExamQuestion[] getQuestions() {
		String query;
		if (type == 1) {
			query = "select r._id, r.image, r.question_en as question, r.cat_id, r.answer, r.answer_nums, r.description_en as description, r.ticket_type "
					+ "from (select t.cat_id, (select q._id from tickets q where q.cat_id = t.cat_id "
					+ "order by random() "
					+ "limit 0,1) ticket_id "
					+ "from tickets t group by t.cat_id order by random() limit 0, 30) u, tickets r "
					+ "where r._id = u.ticket_id order by random()";
		} else {
			query = "select r._id, r.image, r.question_en as question, r.cat_id, r.answer, r.answer_nums, r.description_en as description, r.ticket_type"
					+ " from tickets r where r.cat_id = "
					+ catId
					+ " order by random()";
		}

		Cursor cursor = dbManager.qin(query);
		cnt = cursor.getCount();

		if (cnt == 0) {
			return null;
		}

		ExamQuestion[] eq = new ExamQuestion[cnt];
		int i = 0;
		while (cursor.moveToNext()) {
			int ticket_id = cursor.getInt(0);
			eq[i] = new ExamQuestion(ticket_id, cursor.getString(1),
					cursor.getString(2), getAnswers(ticket_id),
					cursor.getInt(3), cursor.getInt(4), cursor.getInt(5),
					cursor.getString(6), cursor.getInt(7));
			i++;
		}

		return eq;
	}

	/**
	 * returns answers of the question
	 */
	private String[] getAnswers(int ticket_id) {

		String query = "select answer_en from ticket_answers "
				+ "where ticket_id = " + ticket_id + " "
				+ "order by answer_n asc";

		Cursor cursor = dbManager.qin(query);
		int n = cursor.getCount();
		if (n == 0) {
			return null;
		}

		String[] answers = new String[n];
		int i = 0;
		while (cursor.moveToNext()) {
			answers[i] = cursor.getString(0);
			i++;
		}

		return answers;
	}

	/**
	 * Initialize Category properties
	 */
	public void initCatParams() {

		// Initialize current category name
		String query = "select t.name_en from ticket_cats t where t._id = "
				+ catId;
		Cursor cursor = dbManager.qin(query);
		if (cursor.moveToNext()) {
			catName = cursor.getString(0);
		} else {
			catName = null;
		}

		// Initialize next category name
		query = "select t._id, t.name_en from ticket_cats t where t._id > "
				+ catId + " order by t._id asc limit 0, 1";
		cursor = dbManager.qin(query);
		if (cursor.moveToNext()) {
			nextCatId = cursor.getInt(0);
			nextCatName = cursor.getString(1);
		} else {
			nextCatId = 0;
			nextCatName = null;
		}
	}

	/**
	 * Returns questions quantity
	 */
	public int getCnt() {
		return cnt;
	}

	/**
	 * Returns current cat id
	 */
	public int getCurrentCatId() {
		return catId;
	}

	/**
	 * Returns current cat name
	 */
	public String getCurrentCatName() {
		return catName;
	}

	/**
	 * Returns next cat id
	 */
	public int getNextCatId() {
		return nextCatId;
	}

	/**
	 * Returns next cat name
	 */
	public String getNextCatName() {
		return nextCatName;
	}
}
