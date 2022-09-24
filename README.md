# Android_Movie_List_App
Fetching Movies List with Rating From API Using Volley

This topic is a part of [My Complete Andorid Course](https://github.com/ananddasani/Android_Apps)

# App Highlight

![Movie API App1](https://user-images.githubusercontent.com/74413402/192092410-b878a572-ec36-42a7-8218-7ce5cc374d2e.png)
![Movie API App2](https://user-images.githubusercontent.com/74413402/192092413-e7a67f26-5a73-4d32-9b2b-266ba1cd4e25.png)
![Movie API Code](https://user-images.githubusercontent.com/74413402/192092420-c3001b9b-512d-4f10-ac6d-247160a82c3a.png)


# Code

### ModelClass.java
```
public class MovieModel {

    double rating;
    String title;
    String poster;
    String overView;

    public MovieModel(double rating, String title, String poster, String overView) {
        this.rating = rating;
        this.title = title;
        this.poster = poster;
        this.overView = overView;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }
}
```

### CustomAdapter.java
```
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    Context context;
    ArrayList<MovieModel> data;

    public CustomAdapter(Context context, ArrayList<MovieModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.movieName.setText(data.get(position).getTitle());
        holder.movieRating.setText(String.valueOf(data.get(position).getRating()));
        holder.movieDescription.setText(data.get(position).getOverView());

        String uri = data.get(position).getPoster();
        Uri mImage = Uri.parse(uri);
        Glide.with(context).load(mImage).into(holder.movieImage);

        //move to detailed activity with data clicked
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("TITLE", data.get(position).getTitle());
                bundle.putDouble("RATING", data.get(position).getRating());
                bundle.putString("DESCRIPTION", data.get(position).getOverView());
                bundle.putString("POSTER", data.get(position).getPoster());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout constraintLayout;
        ImageView movieImage;
        TextView movieName, movieRating, movieDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            movieName = itemView.findViewById(R.id.textViewMovieName);
            movieRating = itemView.findViewById(R.id.textViewRating);
            movieDescription = itemView.findViewById(R.id.textViewDescription);
            movieImage = itemView.findViewById(R.id.imageViewMoviePoster);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
```

#### MainActivity.java
```
RecyclerView recyclerView;
ArrayList<MovieModel> arrayList;
String url = "https://mocki.io/v1/cd7dc21f-e01e-4469-b58b-01e4eb3a4ba8";

recyclerView = findViewById(R.id.recyclerView);
arrayList = new ArrayList<>();

recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        //fetch all the data from api and collect it into arrayList
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //fetch data from the API
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        double rating = jsonObject.getDouble("rating");
                        String title = jsonObject.getString("title");
                        String poster = jsonObject.getString("poster");
                        String overView = jsonObject.getString("overview");

                        arrayList.add(new MovieModel(rating, title, poster, overView));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //add the request to the queue
        requestQueue.add(jsonArrayRequest);

        //give all the collect data of ArrayList to the adapter
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, arrayList);
        recyclerView.setAdapter(customAdapter);
    }
```

### DetailedActivity.java
```
public class DetailsActivity extends AppCompatActivity {

    TextView movieName, movieRating, movieDescription;
    ImageView movieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        movieName = findViewById(R.id.movieName);
        movieRating = findViewById(R.id.movieRating);
        movieDescription = findViewById(R.id.movieDescription);
        movieImage = findViewById(R.id.movieImage);

        //Extract information from the bundle and show on the UI
        Bundle bundle = getIntent().getExtras();

        String mName = bundle.getString("TITLE");
        double mRating = bundle.getDouble("RATING");
        String mPoster = bundle.getString("POSTER");
        String mOverview = bundle.getString("DESCRIPTION");

        //set the information onto the UI component
        movieName.setText(mName);
        movieRating.setText(String.valueOf(mRating));
        movieDescription.setText(mOverview);

        Glide.with(DetailsActivity.this).load(mPoster).into(movieImage);
    }
}
```

### custom_layout.xml
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/constraintLayout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <ImageView
        android:id="@+id/imageViewMoviePoster"
        android:layout_width="165dp"
        android:layout_height="145dp"
        android:layout_marginStart="8dp"
        android:layout_marginTop="16dp"
        android:layout_marginBottom="16dp"
        android:scaleType="centerCrop"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        tools:srcCompat="@tools:sample/avatars" />

    <TextView
        android:id="@+id/textViewMovieName"
        android:layout_width="180dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginTop="32dp"
        android:fontFamily="sans-serif-condensed"
        android:text="Movie Name"
        android:textColor="@color/black"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintStart_toEndOf="@+id/imageViewMoviePoster"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/textViewRating"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:text="Rating"
        android:textColor="#2196F3"
        android:textStyle="bold"
        app:layout_constraintStart_toEndOf="@+id/imageViewMoviePoster"
        app:layout_constraintTop_toBottomOf="@+id/textViewMovieName" />

    <TextView
        android:id="@+id/textViewDescription"
        android:layout_width="180dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginTop="16dp"
        android:maxLines="3"
        android:minHeight="48dp"
        android:text="Descirption"
        app:layout_constraintStart_toEndOf="@+id/imageViewMoviePoster"
        app:layout_constraintTop_toBottomOf="@+id/textViewRating" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

### detailed_activity.xml
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".DetailsActivity">

    <ImageView
        android:id="@+id/movieImage"
        android:layout_width="0dp"
        android:layout_height="250dp"
        android:scaleType="centerCrop"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        tools:srcCompat="@tools:sample/avatars" />

    <TextView
        android:id="@+id/movieName"
        android:layout_width="300dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginTop="32dp"
        android:text="Movie Name"
        android:textSize="30sp"
        android:textStyle="bold"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/movieImage" />

    <TextView
        android:id="@+id/movieRating"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginTop="8dp"
        android:text="Rating"
        android:textColor="#2196F3"
        android:textSize="16sp"
        android:textStyle="bold"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/movieName" />

    <TextView
        android:id="@+id/movieDescription"
        android:layout_width="350dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginTop="16dp"
        android:text="Long Description"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/movieRating" />
</androidx.constraintlayout.widget.ConstraintLayout>
```
