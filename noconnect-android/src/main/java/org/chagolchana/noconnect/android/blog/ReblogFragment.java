package org.chagolchana.noconnect.android.blog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.nullsafety.MethodsNotNullByDefault;
import org.chagolchana.chagol.api.nullsafety.ParametersNotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.R;
import org.chagolchana.noconnect.android.activity.ActivityComponent;
import org.chagolchana.noconnect.android.controller.handler.UiExceptionHandler;
import org.chagolchana.noconnect.android.controller.handler.UiResultExceptionHandler;
import org.chagolchana.noconnect.android.fragment.BaseFragment;
import org.chagolchana.noconnect.android.view.TextInputView;
import org.chagolchana.noconnect.android.view.TextInputView.TextInputListener;

import javax.annotation.Nullable;
import javax.inject.Inject;

import static android.view.View.FOCUS_DOWN;
import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static org.chagolchana.noconnect.android.activity.BriarActivity.GROUP_ID;
import static org.chagolchana.noconnect.android.blog.BasePostFragment.POST_ID;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class ReblogFragment extends BaseFragment implements TextInputListener {

	public static final String TAG = ReblogFragment.class.getName();

	private ViewHolder ui;
	private GroupId blogId;
	private MessageId postId;
	private BlogPostItem item;

	@Inject
	FeedController feedController;

	static ReblogFragment newInstance(GroupId groupId, MessageId messageId) {
		ReblogFragment f = new ReblogFragment();

		Bundle args = new Bundle();
		args.putByteArray(GROUP_ID, groupId.getBytes());
		args.putByteArray(POST_ID, messageId.getBytes());
		f.setArguments(args);

		return f;
	}

	@Override
	public String getUniqueTag() {
		return TAG;
	}

	@Override
	public void injectFragment(ActivityComponent component) {
		component.inject(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {

		Bundle args = getArguments();
		blogId = new GroupId(args.getByteArray(GROUP_ID));
		postId = new MessageId(args.getByteArray(POST_ID));

		View v = inflater.inflate(R.layout.fragment_reblog, container, false);
		ui = new ViewHolder(v);
		ui.post.setTransitionName(postId);
		ui.input.setSendButtonEnabled(false);
		showProgressBar();

		return v;
	}

	@Override
	public void onStart() {
		super.onStart();

		// TODO: Load blog post when fragment is created. #631
		feedController.loadBlogPost(blogId, postId,
				new UiResultExceptionHandler<BlogPostItem, DbException>(
						this) {
					@Override
					public void onResultUi(BlogPostItem result) {
						item = result;
						bindViewHolder();
					}

					@Override
					public void onExceptionUi(DbException exception) {
						handleDbException(exception);
					}
				});
	}

	private void bindViewHolder() {
		if (item == null) return;

		hideProgressBar();

		ui.post.bindItem(item);
		ui.post.hideReblogButton();

		ui.input.setListener(this);
		ui.input.setSendButtonEnabled(true);
		ui.scrollView.post(new Runnable() {
			@Override
			public void run() {
				ui.scrollView.fullScroll(FOCUS_DOWN);
			}
		});
	}

	@Override
	public void onSendClick(String text) {
		String comment = getComment();
		feedController.repeatPost(item, comment,
				new UiExceptionHandler<DbException>(this) {
					@Override
					public void onExceptionUi(DbException exception) {
						handleDbException(exception);
					}
				});
		finish();
	}

	@Nullable
	private String getComment() {
		if (ui.input.getText().length() == 0) return null;
		return ui.input.getText().toString();
	}

	private void showProgressBar() {
		ui.progressBar.setVisibility(VISIBLE);
		ui.input.setVisibility(GONE);
	}

	private void hideProgressBar() {
		ui.progressBar.setVisibility(INVISIBLE);
		ui.input.setVisibility(VISIBLE);
	}

	private static class ViewHolder {

		private final ScrollView scrollView;
		private final ProgressBar progressBar;
		private final BlogPostViewHolder post;
		private final TextInputView input;

		private ViewHolder(View v) {
			scrollView = (ScrollView) v.findViewById(R.id.scrollView);
			progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
			post = new BlogPostViewHolder(v.findViewById(R.id.postLayout));
			input = (TextInputView) v.findViewById(R.id.inputText);
		}
	}
}
