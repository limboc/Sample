package com.github.limboc.sample.data;

/**
 * Created by Chen on 2016/3/17.
 */
public class SimpleResult<T> {

    private boolean error;
    private int resultCode;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
