
package com.canvas.common;

import static com.android.volley.Request.Method;

public interface Constants {
    public static final boolean DEBUG = true; // true for test env, false for prod env.
    public static final String BASE_URL_PRODUCTION = "hhttps://canvs.cruxcode.nyc/";
    public enum RequestParam {
        GET_MURALS(1, Method.GET, "?requestType=sql&query=activeMurals", "get_murals");
        private int id;
        private int method;
        private String postFix;
        private String requestTag;

        RequestParam(int id, int method, String postFix, String requestTag) {
            this.id = id;
            this.method = method;
            this.postFix = postFix;
            this.requestTag = requestTag;
        }

        public int getId() {
            return id;
        }

        public String getComleteUrl() {
            return BASE_URL_PRODUCTION.concat(postFix);
        }

        public String getOnlyPostFixUrl() {
            return postFix;
        }

        public void setPostFix(String postFix) {
            this.postFix = postFix;
        }

        public String getRequestTag() {
            return requestTag;
        }

        public int getMethod() {
            return method;
        }
    }

    public static final int UPCOMING_ORDERS = 4;
    public static final int IN_PROCESS = 3;
    public static final int ORDERS_READY = 5;
    public static final int IN_LAUNDRY = 6;
    public static final int ALL_ORDERS = 7;
    public static final int SETINGS = 10;
    public static final int LOGOUT = 11;
    public static final int NOTIFICATION = 9;
    public static final String SUCCESS = "200";

    int PROFILE_PIC = 0;
    int COVER_PIC = 1;
    int PROFILE_PIC_FROM_CAMERA = 2;
    int PROFILE_PIC_FROM_FILE = 3;
    int COVER_PIC_FROM_CAMERA = 4;
    int COVER_PIC_FROM_FILE = 5;

    int AUDIO_RECORD_PERMISSION = 1;
    int CALL_PERMISSION = 2;
    int STORAGE_PERMISSION = 3;
    int CALENDAR_PERMISSION = 4;
    int PHONE_STATE_PERMISSION = 5;
    int VIDEO_DOWNLOADING_PERMISSION = 6;
    int CAMERA_PERMISSION = 7;
    int PROFILE_CAMERA_PERMISSION = 8;
    int PROFILE_GALLERY_PERMISSION = 9;
    int PROFILE_CAMERA_STORAGE = 10;
    int ZOPIM_CAMERA_STORAGE = 11;
    int ZOPIM_CAMERA_PERMISSION = 12;

    public final String ASK_ME_ABOUT_KEY = "ask_me_about";
    public final String BIO_KEY = "bio";
    public final String CHAT_USER_ID = "chatUserId";
    public final String DEGREE_KEY = "degree";
    public final String EDUCATION_KEY = "education";
    public final String EMAIL_ID_KEY = "emailId";
    public final String EMPLOYER_KEY = "employer";
    public final String FIRST_NAME_KEY = "firstName";
    public final String IMAGE_URL_KEY = "imageUrl";
    public final String LAST_NAME_KEY = "lastName";
    public final String PHONE_NUMBER_KEY = "phoneNumber";
    public final String ROLE_KEY = "role";
    public final String SCHOOL_NAME_KEY = "schoolName";
    public final String GRADUATION_YEAR_KEY = "gradYear";
    public final String INTEREST_KEY = "interests";
    public final String QUALIFICATIONS_AND_REQUIRED_SKILLS = "qualificationsandrequiredskills";
    public final String QUALIFICATIONS = "qualifications";
    public final String LEARN_ABOUT_KEY = "learnAbout";
    public final String SPECIALIZATION_KEY = "specialization";

    public final String USER_ID_KEY = "userId";
    //JOB RELATED POST PARAMS

    public final String JOB_TITLE_KEY = "jobTitle";
    public final String JOB_START_DATE_KEY = "jobStartDate";
    public final String JOB_SUMMARY_KEY = "jobSummary";
    public final String ORGANIZATION_NAME_KEY = "organizationName";
    public final String EXPERIENCE_REQUIRED_KEY = "experienceRequired";
    public final String REQUIRED_DEGREE_KEY = "requiredDegree";
    public final String JOB_LOCATION_KEY = "jobLocation";
    public final String COMENSATION_KEY = "compensation";
    public final String LOGO_IMAGE_URL_KEY = "logoImageUrl";
    public final String JOB_TYPE_KEY = "jobType";
    public final String RESPONSIBILITIES_KEY = "responsibilities";
    public final String REQUIRED_SKILLS_KEY = "requiredSkills";
    public final String CONTACT_NAME_KEY = "contactName";
    public final String CONTACT_EMAIL_ID_KEY = "contactEmailId";
    public final String JOB_URL_KEY = "jobURL";
    public final String USER_NAME_KEY = "userName";

}
