package com.example.games;

import android.content.Context;

public class LoadDataTask extends android.os.AsyncTask<Void, Void, Void>{
    private Context context;

    public LoadDataTask(Context context) {
        this.context = context.getApplicationContext(); // Use application context
    }

    @Override
    protected Void doInBackground(Void... voids) {
//        TermsAndDefinitions.loadTermsAndDefinitionsfromDB(context);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        // Data is loaded. Update your UI here if needed.
        // For example, notify an adapter if you're using a RecyclerView.
        // Log.d("MainActivity", "Loaded terms: " + TermsAndDefinitions.TsAndDs.size());
    }

}
