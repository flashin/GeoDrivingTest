/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving.modules;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

import com.gedevanishvili.driving.R;
import com.gedevanishvili.driving.GeoDrivingExam;
import com.gedevanishvili.driving.dao.ExamTicketsDao;

/**
 *
 * @author alexx
 * This class is to manage user's exam process
 */
public class ExamManager {
    /*
     * type = 1 exam; type = 2 theme tickets
     */
    private int type;
    private int q;
    private int cnt;
    int catId; //if type is 2
    String catName;
    int nextCatId;
    String nextCatName;
    private Activity context;
    private ExamQuestion[] Questions;
    
    /**
     * Initializes class properties and activates first question
     */
    public ExamManager(Activity context, int t, int catId){
        this.type = t;
        this.catId = catId;
        this.context = context;
        
        //get questions
        ExamTicketsDao examTicketsDao = new ExamTicketsDao(context, type, catId);
        this.Questions = examTicketsDao.getQuestions();
        this.cnt = examTicketsDao.getCnt();
        this.q = 0;
        //category parameters
        if (this.type == 2){
            this.catName = examTicketsDao.getCurrentCatName();
            this.nextCatId = examTicketsDao.getNextCatId();
            this.nextCatName = examTicketsDao.getNextCatName();
        }
        
        //Set Georgian Fonts to the labels
        this.setGeoFont(R.id.question_n_label, context.getString(R.string.question_n));
        this.setGeoFont(R.id.correct_n_label, context.getString(R.string.correct_n));
        this.setGeoFont(R.id.mistakes_n_label, context.getString(R.string.mistakes_n));
        
        //activates first question
        this.activateTicket();
    }
    
    /**
     * Displays question (question displays in image)
     */
    public void activateTicket(){
        //update counts of the correct and wrong answers
        updateCounts();
        
        LinearLayout linearLayout = (LinearLayout)context.findViewById(R.id.image_place);
        //remove old content from question image
        linearLayout.removeAllViews();
        
        //create question image view and set its style
        ImageView touch = new ImageView(context);
        Bitmap bf = BitmapFactory.decodeResource(context.getResources(), Questions[q].getTicketImage(context));
        float scalingFactor = this.getBitmapScalingFactor(bf);
        int scaleHeight = (int) (bf.getHeight() * scalingFactor);
        
        touch.setImageBitmap(bf);
        LinearLayout.LayoutParams imp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, scaleHeight);
        touch.setLayoutParams(imp);
        
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, scaleHeight);
        lp.addRule(RelativeLayout.BELOW, R.id.top_bar);
        linearLayout.setLayoutParams(lp);
        
        linearLayout.addView(touch);
        
        //Check has user answered to question or not
        if (Questions[q].getUserAnswer() > 0){
            //Show correct answer
            showAnswer();
        }
        else {
            //Show answers buttons
            drawButtons();
            context.findViewById(R.id.exam_area).scrollTo(0, 0);
        }
    }
    
    /**
     * draw answer buttons
     */
    public void drawButtons(){
        //get buttons place and remove its old content
        LinearLayout linearLayout = (LinearLayout)context.findViewById(R.id.buttons_place);
        linearLayout.removeAllViews();
        
        //set layout of buttons place
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(0, to_pix(5), 0, to_pix(20));
        
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW, R.id.image_place);
        linearLayout.setLayoutParams(lp);
        
        LinearLayout.LayoutParams ans_btn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ans_btn.leftMargin = to_pix(5);
        ans_btn.rightMargin = to_pix(5);
        
        //get answers quantity of the question
        int max_but = Questions[q].getAnswerNums();
        
        //create buttons array of answers
        Button[] btns = new Button[max_but];
        for (int j = 0; j < max_but; j++){
            //create button with answer number
            btns[j] = new Button(context); 
            btns[j].setPadding(to_pix(19), to_pix(8), to_pix(19), to_pix(8));
            btns[j].setBackgroundColor(Color.rgb(85, 116, 184));
            btns[j].setLayoutParams(ans_btn);
            btns[j].setTextColor(Color.rgb(255, 255, 255));
            btns[j].setTextSize(15);
            btns[j].setText("" + (j+1));
            btns[j].setOnClickListener(myhandler);
            
            //add button to buttons layout view
            linearLayout.addView(btns[j]);
        }
    }
    
    /**
     * Show correct answer after user answers the question
     */
    public void showAnswer(){
        //get buttons place (correct answer will display here) and remove its old content
        LinearLayout linearLayout = (LinearLayout)context.findViewById(R.id.buttons_place);
        linearLayout.removeAllViews();
        
        //Set content layout
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(0, to_pix(5), 0, to_pix(20));
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.BELOW, R.id.image_place);
        linearLayout.setLayoutParams(rlp);
        
        //if question exists
        if (q < cnt){
            RelativeLayout relativeLayout = new RelativeLayout(context);
            relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

            //Create and customize text view for displaying correct answer
            TextView textView = new TextView(context);
            textView.setTypeface(MyResource.getGeoFont(context));
            textView.setText(context.getString(R.string.answer_is) + Questions[q].getAnswer() + " ");
            RelativeLayout.LayoutParams text_par = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            text_par.leftMargin = to_pix(5);
            text_par.rightMargin = to_pix(5);
            text_par.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            textView.setLayoutParams(text_par);
            if (Questions[q].getUserAnswer() == Questions[q].getAnswer()){
                //if answer was correct, show message in blue
                textView.setTextColor(Color.rgb(85, 116, 184));
            }
            else {
                //if answer was incorrect, show message in red
                textView.setTextColor(Color.rgb(171, 13, 19));
            }
            textView.setHeight(to_pix(39));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            relativeLayout.addView(textView);
        
            //Create and customize button to move to the next question
            RelativeLayout.LayoutParams ans_btn = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            ans_btn.leftMargin = to_pix(5);
            ans_btn.rightMargin = to_pix(5);
            ans_btn.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            
            Button nextbut = new Button(context);
            nextbut.setBackgroundColor(Color.rgb(85, 116, 184));
            nextbut.setPadding(to_pix(20), to_pix(9), to_pix(20), to_pix(9));
            nextbut.setLayoutParams(ans_btn);
            nextbut.setTextColor(Color.rgb(255, 255, 255));
            nextbut.setTextSize(15);
            nextbut.setTypeface(MyResource.getGeoFont(context));
            nextbut.setText(context.getString(R.string.but_forward));
            nextbut.setOnClickListener(next_handler);
            relativeLayout.addView(nextbut);
            
            linearLayout.addView(relativeLayout);
            
            //create and customize button to show answer full description
            LinearLayout.LayoutParams desc_par = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            desc_par.topMargin = to_pix(39);
            
            Button descbut = new Button(context);
            descbut.setPadding(0, to_pix(7), 0, to_pix(7));
            descbut.setLayoutParams(desc_par);
            descbut.setBackgroundColor(Color.rgb(85, 116, 184));
            descbut.setTextColor(Color.rgb(255, 255, 255));
            descbut.setTextSize(15);
            descbut.setTypeface(MyResource.getGeoFont(context));
            descbut.setText(R.string.full_answer);
            
            //opens pop up window when user clicks the button
            descbut.setOnClickListener(desc_handler);
            
            linearLayout.addView(descbut);
            }
        }
    
    /**
     * Updates count of correct and incorrect answers
     */
    public void updateCounts(){
        //get current question number and set its content in the sufficient view
        int q_n = (q < cnt) ? (q + 1) : q;
        setTextViewText("question_n_value", "(" + q_n + "/" + cnt + ")");
        
        int true_ans = 0; //how many correct answers
        int false_ans = 0; //how many incorrect answers
        
        for (int i = 0; i < q; i++){
            if (Questions[i].isAnswerCorrect()){
                true_ans++;
            }
            else {
                false_ans++;
            }
        }
        
        //set correct answers quantity to the sufficient view
        setTextViewText("correct_n_value", "" + true_ans);
        //set incorrect answers quantity to the sufficient view
        setTextViewText("mistakes_n_value", "" + false_ans);
    }
    
    /**
     * set text in text view
     */
    public void setTextViewText(String id, String val){
        Resources R = context.getResources();
        int res = R.getIdentifier(id, "id", context.getPackageName());
        TextView textview = (TextView)context.findViewById(res);
        textview.setText(val);
    }
    
    /**
     * Sets user answer by button label
     */
    View.OnClickListener myhandler = new View.OnClickListener() {
        public void onClick(View v) {
            String but_label = ((Button)v).getText().toString();
            int ans = Integer.parseInt(but_label);
            
            Questions[q].setUserAnswer(ans);
            showAnswer();
        }
    };
    
    /**
     * Goes to next question
     */
    View.OnClickListener next_handler = new View.OnClickListener() {
        public void onClick(View v) {
            q++;
            if (q < cnt){
                activateTicket();
            }
            else {
                showResult();
            }
        }
    };
    
    /**
     * Opens window with the answer description
     */
    View.OnClickListener desc_handler = new View.OnClickListener() {
        public void onClick(View v) {
            DescWindow myDescWin = new DescWindow(context, context.getString(R.string.full_answer_desc), Questions[q].getDescription());
            myDescWin.drawPopup();
        }
    };
    
    /**
     * Starts test from the beginning
     */
    View.OnClickListener again_handler = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(context, GeoDrivingExam.class);
            if (type == 2){
                intent.putExtra("EXAM_TYPE", 2);
                intent.putExtra("TICKET_CAT_ID", catId);
            }            
            context.startActivity(intent);
        }
    };
    
    /**
     * Starts next Category test
     */
    View.OnClickListener next_cat_handler = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(context, GeoDrivingExam.class);
            if (type == 2){
                intent.putExtra("EXAM_TYPE", 2);
                intent.putExtra("TICKET_CAT_ID", nextCatId);
            }            
            context.startActivity(intent);
        }
    };
    
    /**
     * sets Georgian font for the given text view
     */
    public void setGeoFont(int id, String cont){
        TextView textview = (TextView)context.findViewById(id);
        textview.setText(cont);
        textview.setTypeface(MyResource.getGeoFont(context));
    }
    
    /**
     * Calculates bitmap scaling factor to match it screen width
     */
    private float getBitmapScalingFactor(Bitmap bm) {
        //Get display width from device
        int displayWidth = context.getWindowManager().getDefaultDisplay().getWidth();

        //Calculate scaling factor and return it
        return ( (float) displayWidth / (float) bm.getWidth() );
    }
    
    /**
     * Shows test result
     */
    public void showResult(){
        //updates correct and incorret answers quantity
        updateCounts();
        
        if (this.type == 1){
            showExamResult();
        }
        else {
            showCatTestResult();
        }
    }
    
    /**
     * shows 30 questions exam result
     */
    public void showExamResult(){
        int true_ans = 0; //how many correct answers
        int false_ans = 0; //how many incorrect answers
    
        for (int i = 0; i < q; i++){
            if (Questions[i].isAnswerCorrect()){
                true_ans++;
            }
            else {
                false_ans++;
            }
        }
        
        //how much time has passed since user began his test
        String testTimer = (String)((TextView)context.findViewById(R.id.test_timer)).getText();
        
        /*
         * Test is successful if user completes test in no more than 30 minutes
         * and has no more than 3 incorrect answers
         */
        boolean exam_success = true;
        if (testTimer.compareTo("00:30:00") > 0 || false_ans > 3){
            exam_success = false;
        }
        
        //get successful or unsuccessfull message of the test
        String resMess = exam_success ? context.getString(R.string.exam_success) : context.getString(R.string.exam_failure);
        int imRes = exam_success ? R.raw.small_icon : R.raw.small_defeat;
        
        //show result image in the image place of the activity
        LinearLayout linearLayout = (LinearLayout)context.findViewById(R.id.image_place);
        linearLayout.removeAllViews();
        linearLayout.setPadding(0, 0, 0, to_pix(20));
        
        ImageView touch = new ImageView(context);
        touch.setImageResource(imRes);
        
        LinearLayout.LayoutParams imPar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        imPar.gravity = Gravity.CENTER_HORIZONTAL;
        touch.setLayoutParams(imPar);
        
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW, R.id.top_bar);
        lp.topMargin = to_pix(20);
        lp.bottomMargin = to_pix(20);
        linearLayout.setLayoutParams(lp);
        
        linearLayout.addView(touch);
        
        //show result text in the buttons place of the activity
        LinearLayout successLayout = (LinearLayout)context.findViewById(R.id.buttons_place);
        successLayout.removeAllViews();
        
        TextView textView = new TextView(context);
        textView.setTypeface(MyResource.getGeoFont(context));
        textView.setText(resMess);
        LinearLayout.LayoutParams textPar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textPar.leftMargin = to_pix(5);
        textPar.rightMargin = to_pix(5);
        textView.setLayoutParams(textPar);
        textView.setTextColor(Color.rgb(171, 13, 19));
        
        RelativeLayout.LayoutParams tp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        tp.addRule(RelativeLayout.BELOW, R.id.image_place);
        successLayout.setLayoutParams(tp);
        
        successLayout.addView(textView);
        
        //create button to start test again
        LinearLayout.LayoutParams startPar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        startPar.topMargin = to_pix(39);
        
        Button startBut = new Button(context);
        startBut.setPadding(0, to_pix(7), 0, to_pix(7));
        startBut.setLayoutParams(startPar);
        startBut.setBackgroundColor(Color.rgb(85, 116, 184));
        startBut.setTextColor(Color.rgb(255, 255, 255));
        startBut.setTextSize(15);
        startBut.setTypeface(MyResource.getGeoFont(context));
        startBut.setText(R.string.but_try_again);
        startBut.setOnClickListener(again_handler);
        
        successLayout.addView(startBut);
    }
    
    /**
     * Show category test result
     */
    public void showCatTestResult(){
        //initialize category parameters
        
        
        //show result text in the image place of the activity
        LinearLayout linearLayout = (LinearLayout)context.findViewById(R.id.image_place);
        linearLayout.removeAllViews();
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(0, to_pix(15), 0, to_pix(20));
        
        //Text after finishing test
        TextView resText = new TextView(context);
        resText.setTypeface(MyResource.getGeoFont(context));
        resText.setText(context.getString(R.string.cat_test_complete));
        LinearLayout.LayoutParams textPar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textPar.leftMargin = to_pix(5);
        textPar.rightMargin = to_pix(5);
        resText.setLayoutParams(textPar);
        resText.setTextColor(Color.rgb(85, 116, 184));
        
        linearLayout.addView(resText);
        
        //show actions in the buttons place of the activity
        LinearLayout actionLayout = (LinearLayout)context.findViewById(R.id.buttons_place);
        actionLayout.removeAllViews();
        
        //Layout for title
        LinearLayout.LayoutParams titlePar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titlePar.leftMargin = to_pix(6);
        titlePar.rightMargin = to_pix(3);
        titlePar.topMargin = to_pix(15);
        titlePar.bottomMargin = to_pix(4);
        
        //Layout for description
        LinearLayout.LayoutParams descPar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        descPar.leftMargin = to_pix(6);
        descPar.rightMargin = to_pix(3);
        descPar.bottomMargin = to_pix(14);
        
        //Try again action
        TextView againTitleText = new TextView(context);
        againTitleText.setTypeface(MyResource.getGeoFont(context));         
        SpannableString spanString = new SpannableString(context.getString(R.string.but_try_again));
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        againTitleText.setText(spanString);
        againTitleText.setLayoutParams(titlePar);
        againTitleText.setTextColor(Color.rgb(171, 13, 19));
        againTitleText.setOnClickListener(again_handler);
        
        linearLayout.addView(againTitleText);
        
        //Try again text
        TextView againDescText = new TextView(context);
        againDescText.setTypeface(MyResource.getGeoFont(context));
        againDescText.setText(catName);
        againDescText.setLayoutParams(descPar);
        againDescText.setTextColor(Color.rgb(16, 1, 130));
        
        linearLayout.addView(againDescText);
        
        //Next Category
        if (nextCatId > 0){
            //next category action
            TextView nextTitleText = new TextView(context);
            nextTitleText.setTypeface(MyResource.getGeoFont(context));         
            spanString = new SpannableString(context.getString(R.string.but_next_cat));
            spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            nextTitleText.setText(spanString);
            nextTitleText.setLayoutParams(titlePar);
            nextTitleText.setTextColor(Color.rgb(171, 13, 19));
            nextTitleText.setOnClickListener(next_cat_handler);

            linearLayout.addView(nextTitleText);

            //next category text
            TextView nextDescText = new TextView(context);
            nextDescText.setTypeface(MyResource.getGeoFont(context));
            nextDescText.setText(nextCatName);
            nextDescText.setLayoutParams(descPar);
            nextDescText.setTextColor(Color.rgb(16, 1, 130));
            
            linearLayout.addView(nextDescText);
        }
    }
    
    /**
     * converts dip to pixels
     */
    private int to_pix(int dip){
        final float scale = context.getResources().getDisplayMetrics().density;
        int in_px = (int) (dip * scale + 0.5f);
        return in_px;
    }
}
