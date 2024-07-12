package com.omniterasoft.springboot.sample.audit;

import com.omniterasoft.springboot.sample.common.CommonConstants;
import com.omniterasoft.springboot.sample.common.util.DateTimeUtils;
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
