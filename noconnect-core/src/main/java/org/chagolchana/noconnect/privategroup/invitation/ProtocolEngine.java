package org.chagolchana.noconnect.privategroup.invitation;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.db.Transaction;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import javax.annotation.Nullable;

@NotNullByDefault
interface ProtocolEngine<S extends Session> {

	S onInviteAction(Transaction txn, S session, @Nullable String message,
			long timestamp, byte[] signature) throws DbException;

	S onJoinAction(Transaction txn, S session) throws DbException;

	S onLeaveAction(Transaction txn, S session) throws DbException;

	S onMemberAddedAction(Transaction txn, S session) throws DbException;

	S onInviteMessage(Transaction txn, S session, InviteMessage m)
			throws DbException, FormatException;

	S onJoinMessage(Transaction txn, S session, JoinMessage m)
			throws DbException, FormatException;

	S onLeaveMessage(Transaction txn, S session, LeaveMessage m)
			throws DbException, FormatException;

	S onAbortMessage(Transaction txn, S session, AbortMessage m)
			throws DbException, FormatException;

}
