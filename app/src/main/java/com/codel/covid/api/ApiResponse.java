package com.codel.covid.api;

import java.util.List;

public class ApiResponse<T> {

        private List<T> posts;
        private String error;

        public ApiResponse(List<T> posts) {
            this.posts = posts;
            this.error = null;
        }

        public ApiResponse(String error) {
            this.error = error;
            this.posts = null;
        }

        public List<T> getPosts() {
            return posts;
        }

        public String getError() {
            return error;
        }

}
