package com.ksoot.common.jpa;

import com.ksoot.common.CommonConstants;
import com.ksoot.common.util.DateTimeUtils;
import org.hibernate.envers.RevisionListener;

public class RevisionEntityListener implements RevisionListener {

  @Override
  public void newRevision(Object revisionEntity) {
    if (revisionEntity instanceof Revision) {
      Revision revisionInfo = (Revision) revisionEntity;
      // revisionInfo.setActor(IdentityHelper.getAuditUserId());
      revisionInfo.setActor(CommonConstants.SYSTEM_USER);
      revisionInfo.setDatetime(DateTimeUtils.nowZonedDateTimeUTC());
    }
  }
}
